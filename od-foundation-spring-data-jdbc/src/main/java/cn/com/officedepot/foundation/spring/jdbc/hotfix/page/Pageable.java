package cn.com.officedepot.foundation.spring.jdbc.hotfix.page;

import java.io.Serializable;

public class Pageable<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int PAGE_SIZE = 20;

	/**
	 * 当前页，从1开始
	 */
	private int num = 0;

	/**
	 * 总记录数
	 */
	private int total = 0;

	/**
	 * 每页的显示数量
	 */
	private int size = PAGE_SIZE;

	/**
	 * 总页数
	 */
	private int count = 0;

	/**
	 * 偏移量，从0开始
	 */
	private int offset = 0;

	/**
	 * 当前页的数量
	 */
	private int limit = 0;

	/**
	 * 第一页的页码
	 */
	private int first = 1;

	/**
	 * 前一页的页码
	 */
	private int previous = 1;

	/**
	 * 下一页的页码
	 */
	private int next = 1;

	/**
	 * 最后一页的页码
	 */
	private int last = 1;

	public Pageable(int total, int num, int size) {
		super();

		if (total < 1) {
			this.total = 0;
			this.size = size < 1 ? PAGE_SIZE : size;
			this.count = 0;
			this.num = num < 1 ? 1 : num;
			this.offset = 0;
			this.limit = 0;
			this.first = 1;
			this.previous = 1;
			this.next = 1;
			this.last = 1;
			return;
		}

		this.total = total;
		this.size = size < 1 ? PAGE_SIZE : size;
		this.count = Integer.valueOf(this.total / this.size + ((this.total % this.size == 0) ? 0 : 1)).intValue();
		this.num = num < 1 ? 1 : (num > this.count ? this.count : num);
		this.offset = (this.num - 1) * this.size;
		this.limit = this.isLast() ? this.total - this.offset : this.size;
		this.first = 1;
		this.last = this.count;
		this.previous = this.isFirst() ? this.first : this.num - 1;
		this.next = this.isLast() ? this.last : this.num + 1;
	}

	public boolean isFirst() {
		return this.num <= 1;
	}

	public boolean hasPrevious() {
		return this.num > 1;
	}

	public boolean hasNext() {
		return this.num < this.count;
	}

	public boolean isLast() {
		return this.num >= this.count;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append("Pageable [");
		builder.append("total=");
		builder.append(total);
		builder.append(", size=");
		builder.append(size);
		builder.append(", count=");
		builder.append(count);
		builder.append(", num=");
		builder.append(num);
		builder.append(", offset=");
		builder.append(offset);
		builder.append(", limit=");
		builder.append(limit);
		builder.append(", first=");
		builder.append(first);
		builder.append(", previous=");
		builder.append(previous);
		builder.append(", next=");
		builder.append(next);
		builder.append(", last=");
		builder.append(last);
		builder.append("]");

		return builder.toString();
	}

}
