package com.seikim.spring.readonly.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.SneakyThrows;

public abstract class BaseRepository<T extends Entity, I> {

	private final AtomicInteger entityId = new AtomicInteger(1);
	protected final ConcurrentHashMap<I, T> entityMap = new ConcurrentHashMap<>();

	/**
	 * 실행시간: 0.5 ~ 2초
	 *
	 * @param entity 저장 및 수정하는 엔티티
	 * @return 데이터베이스에 저장된 최신 엔티티
	 */
	@SneakyThrows
	public T save(final T entity) {
		Thread.sleep((ThreadLocalRandom.current().nextInt(4) + 1) * 500L);    // 0.5 ~ 2초
		if (entity instanceof IntegerEntity e) {
			e.init(entityId.getAndIncrement());
		}
		if (entity instanceof UUIDEntity e) {
			e.init((UUID)entity.getId());
		}
		entityMap.put((I)entity.getId(), entity);
		return entity;
	}

	/**
	 * 실행시간: 0.25 ~ 0.75초
	 *
	 * @param id 데이터 식별자
	 * @return 데이터베이스에서 조회한 엔티티
	 */
	@SneakyThrows
	public Optional<T> findById(final I id) {
		Thread.sleep((ThreadLocalRandom.current().nextInt(3) + 1) * 250L);    // 0.25 ~ 0.75초
		return Optional.ofNullable(entityMap.get(id));
	}

	/**
	 * 실행시간: 0.35 ~ 0.95초
	 *
	 * @param id 데이터 식별자
	 * @return 데이터 삭제 유무
	 */
	@SneakyThrows
	public boolean deleteById(final I id) {
		Optional<T> findOptionalEntity = findById(id);
		if (findOptionalEntity.isEmpty()) {
			return false;
		}
		Thread.sleep((ThreadLocalRandom.current().nextInt(2) + 1) * 100L);    // 0.1 ~ 0.2초
		entityMap.remove(id);
		return true;
	}

	/**
	 * 실행시간: 0.25 ~ 0.75초
	 *
	 * @param id 데이터 식별자
	 * @return 데이터 존재 유무
	 */
	@SneakyThrows
	public boolean existsById(final I id) {
		return findById(id).isPresent();
	}

	/**
	 * 실행시간: 0.25 ~ 0.75초
	 *
	 * @return 데이터베이스에 저장된 모든 데이터
	 */
	@SneakyThrows
	public List<T> findAll() {
		Thread.sleep((ThreadLocalRandom.current().nextInt(3) + 1) * 250L);    // 0.25 ~ 0.75초
		return new ArrayList<>(entityMap.values());
	}
}
