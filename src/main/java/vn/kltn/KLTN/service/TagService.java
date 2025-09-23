package vn.kltn.KLTN.service;

import java.util.List;

import vn.kltn.KLTN.entity.Tag;

public interface TagService {
	public boolean update(Tag tag);

	public boolean deleteById(int tagId);

	public Tag findById(int tagId);

	public Tag add(Tag tag);

	public List<Tag> findAll();
}
