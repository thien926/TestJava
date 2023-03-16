package com.laptrinhjavaweb.response;

public class PagedResponse<T> extends Response<T> {
	private Integer pageNo;
    private Integer pageSize;
    private Integer pageCount;
    private Integer totalRecordCount;
    
	public PagedResponse(boolean succeeded, String[] errors, Integer pageNo, Integer pageSize, Integer pageCount,
			Integer totalRecordCount) {
		super(succeeded, errors);
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.pageCount = pageCount;
		this.totalRecordCount = totalRecordCount;
	}
	
	public PagedResponse(T data, boolean succeeded, String[] errors, String message, Integer pageNo, Integer pageSize, Integer pageCount,
			Integer totalRecordCount) {
		super(succeeded, message, errors, data);
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.pageCount = pageCount;
		this.totalRecordCount = totalRecordCount;
	}

	public Integer getPageNo() {
		return pageNo;
	}
	
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	
	public Integer getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	public Integer getPageCount() {
		return pageCount;
	}
	
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	
	public Integer getTotalRecordCount() {
		return totalRecordCount;
	}
	
	public void setTotalRecordCount(Integer totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}
}
