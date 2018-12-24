package org.mpatapenka.ssp.service.impl;

import lombok.RequiredArgsConstructor;
import org.mpatapenka.ssp.domain.Size;
import org.mpatapenka.ssp.entity.SizeEntity;
import org.mpatapenka.ssp.repository.SizeRepository;
import org.mpatapenka.ssp.service.SizeService;
import org.mpatapenka.ssp.transform.Transformer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class SizeServiceImpl implements SizeService {
    private final SizeRepository sizeRepository;
    private final Transformer<SizeEntity, Size> sizeTransformer;

    @Override
    public Collection<Size> getAll() {
        return StreamSupport.stream(sizeRepository.findAll().spliterator(), true)
                .map(sizeTransformer::forward)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Size> getAll(long categoryId) {
        return sizeRepository.findByCategoriesIdOrderByCategoriesNumericSymbolicAsc(Collections.singleton(categoryId)).stream()
                .filter(SizeEntity::isArchived)
                .map(sizeTransformer::forward)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Size> getAllActive() {
        return StreamSupport.stream(sizeRepository.findAll().spliterator(), true)
                .filter(SizeEntity::isArchived)
                .map(sizeTransformer::forward)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void saveAll(Collection<Size> sizes) {
        sizes.parallelStream()
                .filter(Objects::nonNull)
                .forEach(size -> sizeRepository.findById(size.getId())
                        .ifPresentOrElse(sizeEntity -> sizeEntity.setArchived(size.isArchived()),
                                () -> sizeRepository.save(sizeTransformer.backward(size))));
    }

    @Override
    @Transactional
    public void removeAll(Collection<Long> sizeIds) {
        sizeRepository.deleteAll(sizeRepository.findAllById(sizeIds));
    }
}