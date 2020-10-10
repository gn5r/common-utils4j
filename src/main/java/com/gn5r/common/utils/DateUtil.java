package com.gn5r.common.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import org.apache.commons.lang3.time.DateUtils;

/**
 * {@link DateUtils}拡張クラス
 * 
 * <ul>
 * <li><b>getNendoHanki</b> - 指定した日付の年度半期を取得する</li>
 * <li><b>getNendo</b> - 指定した日付の年度を取得する</li>
 * <li><b>getHanki</b> - 指定した日付の半期を取得する</li>
 * <li><b>diff</b> - 指定した日付の年度半期の差分を取得する</li>
 * <li><b>diff</b> - 今日と指定した日付の年度半期の差分を取得する</li>
 * </ul>
 * 
 * @author gn5r
 * @since 0.1.1-RELEASE
 */
public final class DateUtil extends DateUtils {

    /**
     * yyyy/MM/dd HH:mm:ss 形式に変換する
     */
    public static final DateTimeFormatter YMD_HMS = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    /**
     * yyyy/MM/dd 形式に変換する
     */
    public static final DateTimeFormatter YMD = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    /**
     * yyyy 形式に変換する
     */
    public static final DateTimeFormatter YYYY = DateTimeFormatter.ofPattern("yyyy");

    /**
     * MM 形式に変換する
     */
    public static final DateTimeFormatter MM = DateTimeFormatter.ofPattern("MM");

    /**
     * dd 形式に変換する
     */
    public static final DateTimeFormatter DD = DateTimeFormatter.ofPattern("dd");

    /**
     * HH:mm:ss 形式に変換する
     */
    public static final DateTimeFormatter HMS = DateTimeFormatter.ofPattern("HH:mm:ss");

    /**
     * 日付文字列から年度+上半期/下半期を取得する
     * <p>
     * 例)
     * </p>
     * <ul>
     * <li>2020/03/01 を引数で与えた場合は2019年度下半期 → 2020.5 を返却する</li>
     * <li>2020/04/12 を引数で与えた場合は2020年度上半期 → 2020 を返却する</li>
     * </ul>
     * 
     * @param date 日付文字列
     * @return 年度+上半期/下半期
     * @since 0.3.0-RELEASE
     */
    public static final double getNendoHanki(final String date) {
        double result = 0;

        final int nendo = getNendo(date);
        final double hanki = getHanki(date);

        result = Double.valueOf(String.valueOf(nendo)) + hanki;

        return result;
    }

    /**
     * 2つの日付から年度半期の差分を求める
     * <p>
     * aの年度半期 - bの年度半期を返却
     * </p>
     * 
     * @param a 日付文字列a
     * @param b 日付文字列b
     * @return 差分
     * @since 0.3.0-RELEASE
     */
    public static final double diff(final String a, final String b) {
        final double A = getNendoHanki(a);
        final double B = getNendoHanki(b);

        return A - B;
    }

    /**
     * システム日付の今日と指定した日付の年度半期の差分を求める
     * <p>
     * 今日の年度半期 - bの年度半期を返却
     * </p>
     * 
     * @param a 日付文字列
     * @return 今日との差分
     * @since 0.3.2-RELEASE
     */
    public static final double diff(final String a) {
        final double now = getNendoHanki(null);
        final double A = getNendoHanki(a);

        return now - A;
    }

    /**
     * 日付文字列から年度を取得する
     * 
     * @param date 日付文字列
     * @return 年度
     * @since 0.3.0-RELEASE
     */
    public static final int getNendo(final String date) {
        LocalDate localDate = parseDate(date);
        localDate = localDate.minusMonths(3);

        return localDate.getYear();
    }

    /**
     * 日付文字列から上半期/下半期を取得する
     * <ul>
     * <li><b>0</b>:上半期</li>
     * <li><b>0.5</b>:下半期</li>
     * </ul>
     * 
     * @param date 日付文字列
     * @return 上半期/下半期
     * @since 0.3.0-RELEASE
     */
    public static final double getHanki(final String date) {
        final int month = parseDate(date).getMonthValue();

        if (1 <= month && month <= 3) {
            return 0.5;
        } else if (4 <= month && month <= 9) {
            return 0;
        } else if (10 <= month && month <= 12) {
            return 0.5;
        }

        return 0;
    }

    /**
     * 日付文字列から {@link LocalDate} に変換する
     * 
     * @param date 日付文字列
     * @return {@link LocalDate} オブジェクト
     * @since 0.3.0-RELEASE
     */
    private static final LocalDate parseDate(final String date) {
        LocalDate localDate = LocalDate.now();

        if (Objects.isNull(date)) {
            return localDate;
        }

        return LocalDate.parse(date.replaceAll("\\W", "-"));
    }
}