package com.ashen.petcommon.utils;

import com.ashen.petcommon.model.BasePage;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.function.Supplier;

public final class PageSelectUtils {

    private PageSelectUtils() {}

    public enum SelectType {
        LIST,
        COUNT,
        COUNT_AND_LIST;

        public static SelectType of(String type) {
            if (type == null) {
                return COUNT_AND_LIST;
            }
            try {
                return SelectType.valueOf(type.toUpperCase());
            } catch (IllegalArgumentException e) {
                return COUNT_AND_LIST;
            }
        }
    }

    /**
     * 根据 selectType 执行分页：
     * - LIST：仅列表（禁用 count 查询）
     * - COUNT：仅总数（运行一次分页查询，取 total，丢弃 list）
     * - COUNT_AND_LIST：列表 + 总数
     *
     * @param basePage     继承 BasePage 的 DTO（含 pageNum/pageSize/selectType）
     * @param listSupplier 列表查询逻辑（如 () -> super.list()）
     */
    public static <T> PageInfo<T> selectPage(BasePage basePage, Supplier<List<T>> listSupplier) {
        int pageNum  = basePage.getPageNum()  == null ? 1  : Math.max(basePage.getPageNum(), 1);
        int pageSize = basePage.getPageSize() == null ? 10 : Math.max(basePage.getPageSize(), 1);
        SelectType type = SelectType.of(basePage.getSelectType());

        switch (type) {
            case COUNT -> {
                long total = PageHelper.count(listSupplier::get); // 只执行 count(*)
                PageInfo<T> pi = new PageInfo<>(List.of());
                pi.setPageNum(pageNum);
                pi.setPageSize(pageSize);
                pi.setTotal(total);
                return pi;
            }
            case LIST -> {
                PageInfo<T> pi = PageHelper.startPage(pageNum, pageSize, false)
                                           .doSelectPageInfo(listSupplier::get); // 不跑 count
                pi.setTotal(-1); // 表示未查询总数
                return pi;
            }
            case COUNT_AND_LIST -> {
                return PageHelper.startPage(pageNum, pageSize, true)
                                 .doSelectPageInfo(listSupplier::get); // 正常 count + list
            }
            default -> {
                return PageHelper.startPage(pageNum, pageSize, true)
                                 .doSelectPageInfo(listSupplier::get);
            }
        }
    }
}