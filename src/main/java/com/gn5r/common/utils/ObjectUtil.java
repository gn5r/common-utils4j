package com.gn5r.common.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.gn5r.common.resource.Difference;

import org.apache.commons.lang3.ObjectUtils;

/**
 * <p>
 * {@link ObjectUtils}拡張クラス
 * </p>
 *
 * <ul>
 * <li><b>diff</b> - クラスオブジェクトのフィールドパラメータを比較する。パラメータに相違があれば {@link Difference}
 * のリストを返却する。相違がなければ空のリストを返却する</li>
 * <li><b>check</b> - クラスオブジェクトのフィールドパラメータを比較する。パラメータに相違があれば true を、相違がなければ
 * false を返却する</li>
 * <li><b>getFieldNames</b> - オブジェクトのフィールドリストを返却する</li>
 * <li><b>getSameFieldNames</b> - 指定した2つのフィールドリストから同一のフィールドリストを返却する</li>
 * </ul>
 *
 * @author gn5r
 * @since 0.1.2-RELEASE
 * @see ObjectUtils
 */
public final class ObjectUtil extends ObjectUtils {

    /**
     * クラスオブジェクトの同一フィールドパラメータを比較する。パラメータに相違があれば
     * {@link Difference}のリストを返却する。相違がなければ空のリストを返却する
     * <p>
     * 同一フィールドが存在しない場合はパラメータの比較をしないので空のリストが返却される
     * </p>
     * 
     * @param <T>      比較するオブジェクトタイプ
     * @param a        オブジェクトa
     * @param b        オブジェクトb
     * @param excludes 除外フィールド名のString配列
     * @return {@link Difference} 相違フィールドリスト
     * @throws NullPointerException     オブジェクトaまたはオブジェクトbが {@code null} の場合にthrowする
     * @throws NoSuchFieldException     オブジェクトaまたはオブジェクトbのフィールドが見つからない場合にthrowする
     * @throws IllegalArgumentException オブジェクトaまたはオブジェクトbのフィールドにアクセスできなかった場合にthrowする
     * @since 0.1.4-RELEASE
     */
    public static final <T> List<Difference> diff(T a, T b, String... excludes) {
        List<Difference> diffList = new ArrayList<>();

        // チェック処理
        checkObjectNull(a, b);

        // 同一フィールドリスト
        List<String> sameField = new ArrayList<>();

        // オブジェクトaのフィールド一覧を取得
        List<String> fieldAList = getFieldNames(a, excludes);

        // オブジェクトbのフィールド一覧を取得
        List<String> fieldBList = getFieldNames(b, excludes);

        // オブジェクトaとオブジェクトbの同一フィールドを取得する
        sameField = getSameFieldNames(fieldAList, fieldBList);

        for (String name : sameField) {
            try {
                Field fieldA = a.getClass().getDeclaredField(name);
                fieldA.setAccessible(true);
                Field fieldB = b.getClass().getDeclaredField(name);
                fieldB.setAccessible(true);

                try {
                    final Object paramA = fieldA.get(a);
                    final Object paramB = fieldB.get(b);

                    if (!Objects.equals(paramA, paramB)) {
                        final Difference e = new Difference(name, paramA, paramB);
                        diffList.add(e);
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }

            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        return diffList;
    }

    /**
     * クラスオブジェクトのフィールドパラメータを比較する。パラメータに相違があれば true を、相違がなければ false を返却する。
     * 
     * @param <T>      比較するオブジェクトタイプ
     * @param a        オブジェクトa
     * @param b        オブジェクトb
     * @param excludes 除外フィールド名のString配列
     * @return 相違有無
     * @throws NullPointerException     オブジェクトaまたはオブジェクトbが {@code null} の場合にthrowする
     * @throws NoSuchFieldException     オブジェクトaまたはオブジェクトbのフィールドが見つからない場合にthrowする
     * @throws IllegalArgumentException オブジェクトaまたはオブジェクトbのフィールドにアクセスできなかった場合にthrowする
     * @since 0.1.4-RELEASE
     */
    public static final <T> boolean check(T a, T b, String... excludes) {
        // チェック処理
        checkObjectNull(a, b);

        // 同一フィールドリスト
        List<String> sameField = new ArrayList<>();

        // オブジェクトaのフィールド一覧を取得
        List<String> fieldAList = getFieldNames(a, excludes);

        // オブジェクトbのフィールド一覧を取得
        List<String> fieldBList = getFieldNames(b, excludes);

        // オブジェクトaとオブジェクトbの同一フィールドを取得する
        sameField = getSameFieldNames(fieldAList, fieldBList);

        for (String name : sameField) {
            try {
                Field fieldA = a.getClass().getDeclaredField(name);
                fieldA.setAccessible(true);
                Field fieldB = b.getClass().getDeclaredField(name);
                fieldB.setAccessible(true);

                try {
                    final Object paramA = fieldA.get(a);
                    final Object paramB = fieldB.get(b);

                    // フィールドパラメータが一致しなければtrueを返却する
                    if (!Objects.equals(paramA, paramB)) {
                        return true;
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }

            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * オブジェクトがnullかどうかをチェックし、nullであるならば {@link NullPointerException} をthrowする
     * 
     * @param a オブジェクトa
     * @param b オブジェクトb
     * @throws NullPointerException オブジェクトaまたはオブジェクトbがNullの場合にthrowする
     */
    private static final void checkObjectNull(Object a, Object b) {
        if (Objects.isNull(a)) {
            throw new NullPointerException("オブジェクトaがnullです");
        }

        if (Objects.isNull(b)) {
            throw new NullPointerException("オブジェクトbがnullです");
        }
    }

    /**
     * オブジェクトのフィールドリストを返却する
     * 
     * @param object   フィールド一覧を取得したいオブジェクト
     * @param excludes 除外フィールド名のString配列
     * @return フィールドリスト
     * @since 0.1.5
     */
    public static final List<String> getFieldNames(Object object, String... excludes) {
        // 念のためthis$0を除外しておく
        return Arrays.asList(object.getClass().getDeclaredFields()).stream().map(Field::getName)
                .filter(name -> !Arrays.asList(excludes).contains(name) && !name.matches("this\\$0"))
                .collect(Collectors.toList());
    }

    /**
     * 指定した2つのフィールドリストから同一のフィールドリストを返却する
     * 
     * @param a オブジェクトaのフィールドリスト
     * @param b オブジェクトbのフィールドリスト
     * @return 同一フィールドリスト
     * @throws IllegalArgumentException 同一フィールドが存在しない場合にthrowする
     * @since 0.1.8
     */
    public static final List<String> getSameFieldNames(List<String> a, List<String> b) {
        final List<String> fieldList = a.stream().filter(name -> b.contains(name)).collect(Collectors.toList());

        // 同一フィールドが存在しない場合は例外とする
        if (fieldList.isEmpty()) {
            throw new IllegalArgumentException("同一フィールドが存在しません");
        }

        return fieldList;
    }
}