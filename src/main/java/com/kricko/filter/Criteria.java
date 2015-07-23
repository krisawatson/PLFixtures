package com.kricko.filter;

import java.util.List;

import com.kricko.domain.Fixture;

public interface Criteria {
	public List<Fixture> meetCriteria(List<Fixture> fixtures);
}
