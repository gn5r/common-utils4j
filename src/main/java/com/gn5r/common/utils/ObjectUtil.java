package com.gn5r.common.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.gn5r.common.resource.Difference;

import org.apache.commons.lang3.ObjectUtils;

/**
 * <p>
 * {@linkplain ObjectUtils}拡張クラス
 * </p>
 *
 * <ul>
 * <li><b>diff</b> - 同一クラスオブジェクトのフィールドパラメータを比較する。パラメータに相違があれば
 * {@linkplain Difference}のリストを返却する。相違がなければ空のリストを返却する。</li>
 * <li><b>check</b>同一クラスオブジェクトのフィールドパラメータを比較する。パラメータに相違があれば {@value true}
 * を、相違がなければ {@value false} を返却する。</li>
 * </ul>
 *
 * @exception {@link NullPointerException} 第1引数または第2引数のオブジェクトが {@code null}
 *                   の場合にthrowする
 * @exception {@link IllegalArgumentException}
 *                   オブジェクトaまたはオブジェクトbのフィールドにアクセスできなかった場合にthrowする
 * @author gn5r
 * @since 0.1.2-RELEASE
 * @see ObjectUtils
 */
public final class ObjectUtil extends ObjectUtils {

    /**
     * 同一クラスオブジェクトのフィールドパラメータを比較する。パラメータに相違があれば
     * {@linkplain Difference}のリストを返却する。相違がなければ空のリストを返却する
     * 
     * @param <T>      比較されるオブジェクトのタイプ
     * @param a        比較対象a
     * @param b        比較対象b
     * @param excludes 除外フィールド名配列
     * @return {@linkplain Difference} 相違フィールドリスト
     */
    public static final <T> List<Difference> diff(T a, T b, String ...excludes) {
        List<Difference> diffList = new ArrayList<Difference>();

        commonProccess(a, b);

        for (Field field : a.getClass().getDeclaredFields()) {

            // privateフィールドにアクセスするには #setAccessible をtrueにする必要がある
            field.setAccessible(true);
            final String fieldName = field.getName();

            // 除外フィールドは比較しない
            if (!Objects.isNull(excludes) && Arrays.asList(excludes).contains(fieldName))
                continue;

            try {
                final Object paramA = field.get(a);
                final Object paramB = field.get(b);

                if (!Objects.equals(paramA, paramB)) {
                    Difference e = new Difference(fieldName, paramA, paramB);
                    diffList.add(e);
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return diffList;
    }

    /**
     * 同一クラスオブジェクトのフィールドパラメータを比較する。パラメータに相違があれば {@value true} を、相違がなければ
     * {@value false} を返却する。
     * 
     * @param <T>      比較されるオブジェクトのタイプ
     * @param a        比較対象a
     * @param b        比較対象b
     * @param excludes 除外フィールド名配列
     * @return 相違有無
     */
    public static final <T> boolean check(T a, T b, String... excludes) {
        commonProccess(a, b);

        // privateフィールドにアクセスするには #getDeclaredFields を使う必要がある
        for (Field field : a.getClass().getDeclaredFields()) {
            final String fieldName = field.getName();

            // 除外フィールドは比較しない
            if (!Objects.isNull(excludes) && Arrays.asList(excludes).contains(fieldName))
                continue;

            try {
                final Object paramA = field.get(a);
                final Object paramB = field.get(b);

                if (Objects.equals(paramA, paramB)) {
                    return true;
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * ObjectUtilクラス共通処理
     * 
     * @param a オブジェクトa
     * @param b オブジェクトb
     */
    private static final void commonProccess(Object a, Object b) {
        if (Objects.isNull(a)) {
            throw new NullPointerException("オブジェクトaがnullです");
        }

        if (Objects.isNull(b)) {
            throw new NullPointerException("オブジェクトbがnullです");
        }

        final String classNameA = a.getClass().getName();
        final String classNameB = b.getClass().getName();

        if (!Objects.equals(classNameA, classNameB)) {
            throw new IllegalArgumentException("同一クラス以外では比較できません");
        }
    }
}