package com.artonhanger.manage.respository.core;

import com.artonhanger.manage.annotation.AohDataTest;
import com.artonhanger.manage.model.Artist;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

@AohDataTest
public abstract class RepositoryCoreTest {
    public abstract void 전체_레코드_조회_테스트();
    public abstract void 특정_레코드_조회_테스트();
    public abstract void 레코드_추가_테스트();
    public abstract void 레코드_수정_테스트();
    public abstract void 레코드_삭제_테스트();
}
