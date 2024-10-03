package com.seikim.spring.readonly.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

import com.seikim.spring.readonly.common.id.IdentifierGenerate;
import com.seikim.spring.readonly.common.strategy.GenerateIdentifierStrategy;

import lombok.SneakyThrows;

public abstract class BaseRepository<V extends Entity<?>, T> {

	protected final ConcurrentHashMap<T, V> entityMap = new ConcurrentHashMap<>();
	private final IdentifierGenerate<T> identifierGenerate;

	private final Map<Class<?>, GenerateIdentifierStrategy<?>> generateIdentifierStrategies;

	public BaseRepository(
			Map<Class<?>, GenerateIdentifierStrategy<?>> generateIdentifierStrategies,
			IdentifierGenerate<T> identifierType
	) {
		this.generateIdentifierStrategies = generateIdentifierStrategies;
		this.identifierGenerate = identifierType;
	}

	/**
	 * SAVE 실행시간: 1 ~ 2초
	 * UPDATE 실행시간: 0.5 ~ 1초
	 *
	 * @param entity 저장 및 수정하는 엔티티
	 * @return 데이터베이스에 저장된 최신 엔티티
	 */
	@SuppressWarnings("unchecked")
	@SneakyThrows
	public V save(V entity) {
		Thread.sleep((ThreadLocalRandom.current().nextInt(2) + 1) * 500L);    // 0.5 ~ 1초
		GenerateIdentifierStrategy<T> strategy
				= (GenerateIdentifierStrategy<T>)generateIdentifierStrategies.get(entity.getIdentifierClassType());
		if (entity.getId() == null) {
			entity = (V)strategy.generate(identifierGenerate.generate(), entity);
			Thread.sleep((ThreadLocalRandom.current().nextInt(2) + 1) * 500L);    // 0.5 ~ 1초
		}
		entityMap.put((T)entity.getId(), entity);
		return entity;
	}

	/**
	 * 실행시간: 0.25 ~ 0.75초
	 *
	 * @param id 데이터 식별자
	 * @return 데이터베이스에서 조회한 엔티티
	 */
	@SneakyThrows
	public Optional<V> findById(final T id) {
		Thread.sleep((ThreadLocalRandom.current().nextInt(3) + 1) * 1_250L);    // 0.25 ~ 0.75초
		return Optional.ofNullable(entityMap.get(id));
	}

	/**
	 * 실행시간: 0.35 ~ 0.95초
	 *
	 * @param id 데이터 식별자
	 * @return 데이터 삭제 유무
	 */
	@SneakyThrows
	public boolean deleteById(final T id) {
		Optional<V> findOptionalEntity = findById(id);
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
	public boolean existsById(final T id) {
		return findById(id).isPresent();
	}

	/**
	 * 실행시간: 0.25 ~ 0.75초
	 *
	 * @return 데이터베이스에 저장된 모든 데이터
	 */
	@SneakyThrows
	public List<V> findAll() {
		Thread.sleep((ThreadLocalRandom.current().nextInt(3) + 1) * 250L);    // 0.25 ~ 0.75초
		return new ArrayList<>(entityMap.values());
	}
}
