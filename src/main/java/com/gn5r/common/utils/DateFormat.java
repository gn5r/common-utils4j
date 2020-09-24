package com.gn5r.common.utils;

import java.time.format.DateTimeFormatter;

/**
 * 日付フォーマットクラス
 * 
 * @author gn5r
 * @since 0.1.1-RELEASE
 */
public final class DateFormat {

    /**
     * yyyy/MM/dd HH:mm:ss 形式に変換する
     */
    public final DateTimeFormatter YMD_HMS = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    /**
     * yyyy/MM/dd 形式に変換する
     */
    public final DateTimeFormatter YMD = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    /**
     * yyyy 形式に変換する
     */
    public final DateTimeFormatter YYYY = DateTimeFormatter.ofPattern("yyyy");

    /**
     * MM 形式に変換する
     */
    public final DateTimeFormatter MM = DateTimeFormatter.ofPattern("MM");

    /**
     * dd 形式に変換する
     */
    public final DateTimeFormatter DD = DateTimeFormatter.ofPattern("dd");

    /**
     * HH:mm:ss 形式に変換する
     */
    public final DateTimeFormatter HMS = DateTimeFormatter.ofPattern("HH:mm:ss");
}