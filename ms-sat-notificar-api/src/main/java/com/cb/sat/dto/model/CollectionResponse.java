package com.cb.sat.dto.model;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CollectionResponse<T> implements Serializable {

	private static final long serialVersionUID = -9200305032387823549L;
	private List<T> elements;
	private Integer start;
	private String sort;
	private Integer limit = Constantes.PAGINATION_SIZE;
	private Long totalCount;

	public CollectionResponse(List<T> elements) {
		super();
		this.elements = elements;
	}

	public CollectionResponse(List<T> elements, Integer start, Integer limit, Long totalCount) {
		super();
		this.elements = elements;
		this.start = start;
		this.limit = limit;
		this.totalCount = totalCount;
	}

}
