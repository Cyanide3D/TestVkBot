package com.cyanide3d;

import com.cyanide3d.lib.mylittleorm.repo.Repository;
import com.cyanide3d.models.TestModel;

import java.util.List;

public interface TestRepo extends Repository<Integer, TestModel> {

    List<TestModel> findByLastname(String lastname);

}
