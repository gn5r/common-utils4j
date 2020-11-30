package com.gn5r.common.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
     * @param a        オブジェクトa
     * @param b        オブジェクトb
     * @param excludes 除外フィールド名のString配列
     * @return {@link Difference} 相違フィールドリスト
     * @throws NullPointerException     オブジェクトaまたはオブジェクトbが {@code null} の場合にthrowする
     * @throws NoSuchFieldException     オブジェクトaまたはオブジェクトbのフィールドが見つからない場合にthrowする
     * @throws IllegalArgumentException オブジェクトaまたはオブジェクトbのフィールドにアクセスできなかった場合にthrowする
     * @since 0.1.4-RELEASE
     */
    public static final List<Difference> diff(Object a, Object b, String... excludes) {
        List<Difference> diffList = new ArrayList<>();

        // チェック処理
        checkObjectNull(a, b);

        // 同一フィールドリスト
        final List<String> sameField = getSameFieldNames(a, b, excludes);

        for (String name : sameField) {
            try {
                Field fieldA = a.getClass().getDeclaredField(name);
                fieldA.setAccessible(true);
                Field fieldB = b.getClass().getDeclaredField(name);
                fieldB.setAccessible(true);

                final Object paramA = fieldA.get(a);
                final Object paramB = fieldB.get(b);

                if (!Objects.equals(paramA, paramB)) {
                    final Difference e = new Difference(name, paramA, paramB);
                    diffList.add(e);
                }

            } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return diffList;
    }

    /**
     * クラスオブジェクトのフィールドパラメータを比較する。パラメータに相違があれば true を、相違がなければ false を返却する。
     * 
     * @param a        オブジェクトa
     * @param b        オブジェクトb
     * @param excludes 除外フィールド名のString配列
     * @return 相違有無
     * @throws NullPointerException     オブジェクトaまたはオブジェクトbが {@code null} の場合にthrowする
     * @throws NoSuchFieldException     オブジェクトaまたはオブジェクトbのフィールドが見つからない場合にthrowする
     * @throws IllegalArgumentException オブジェクトaまたはオブジェクトbのフィールドにアクセスできなかった場合にthrowする
     * @since 0.1.4-RELEASE
     */
    public static final boolean check(Object a, Object b, String... excludes) {
        // チェック処理
        checkObjectNull(a, b);

        // 同一フィールドリスト
        final List<String> sameField = getSameFieldNames(a, b, excludes);

        for (String name : sameField) {
            try {
                Field fieldA = a.getClass().getDeclaredField(name);
                fieldA.setAccessible(true);
                Field fieldB = b.getClass().getDeclaredField(name);
                fieldB.setAccessible(true);

                final Object paramA = fieldA.get(a);
                final Object paramB = fieldB.get(b);

                // フィールドパラメータが一致しなければtrueを返却する
                if (!Objects.equals(paramA, paramB)) {
                    return true;
                }

            } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * クラスオブジェクトの同一フィールドパラメータを比較する。一致したパラメータがあれば {@link Difference}
     * のリストを返却する。一致したパラメータが見つからなければ空のリストを返却する
     * 
     * @param a        オブジェクトa
     * @param b        オブジェクトb
     * @param excludes 除外フィールド名のString配列
     * @return {@link Difference} 一致フィールドリスト
     * @throws NullPointerException     オブジェクトaまたはオブジェクトbが {@code null} の場合にthrowする
     * @throws NoSuchFieldException     オブジェクトaまたはオブジェクトbのフィールドが見つからない場合にthrowする
     * @throws IllegalArgumentException オブジェクトaまたはオブジェクトbのフィールドにアクセスできなかった場合にthrowする
     * @since 0.3.4
     */
    public static final List<Difference> same(Object a, Object b, String... excludes) {
        checkObjectNull(a, b);
        List<Difference> sameField = new ArrayList<>();

        final List<String> fieldNames = getSameFieldNames(a, b, excludes);

        for (String name : fieldNames) {
            try {
                Field fieldA = a.getClass().getDeclaredField(name);
                fieldA.setAccessible(true);
                Field fieldB = b.getClass().getDeclaredField(name);
                fieldB.setAccessible(true);

                final Object paramA = fieldA.get(a);
                final Object paramB = fieldB.get(b);

                if (Objects.equals(paramA, paramB)) {
                    final Difference e = new Difference(name, paramA, paramB);
                    sameField.add(e);
                }
            } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return sameField;
    }

    /**
     * オブジェクトを文字列に変換する
     * 
     * <pre>
     * public class Test {
     *   int id;
     *   Stirng name;
     * 
     *   ...
     * 
     *   public String toString() {
     *     return ObjectUtil.toString(this);
     *   }
     * }
     * </pre>
     * 
     * <p>
     * オブジェクトのtoStringメソッドを上記のようにすると {@code Test[id=0, name=test]} と表示される
     * </p>
     * 
     * @param object
     * @return オブジェクト名及びオブジェクトの中身
     * @param excludes 除外フィールド名のString配列
     * @since 0.3.8
     */
    public static final String toString(Object object, String... excludes) {
        if (Objects.isNull(object)) {
            throw new NullPointerException("オブジェクトがnullです");
        }

        final Map<String, Object> map = toMap(object, excludes);
        StringBuffer buffer = new StringBuffer();

        buffer.append(object.getClass().getSimpleName() + "[");

        for (Map.Entry<String, Object> field : map.entrySet()) {
            final String str = field.getKey() + "=" + field.getValue();
            buffer.append(str + ", ");
        }

        final String result = buffer.toString().replaceAll(",\\s$", "");

        return result.concat("]");
    }

    /**
     * <p>
     * オブジェクトを文字列に変換する
     * </p>
     * 
     * <pre>
     * public class Test {
     *   int id;
     *   Stirng name;
     * 
     *   ...
     * 
     *   public String toString() {
     *     return ObjectUtil.toString(this);
     *   }
     * }
     * </pre>
     * 
     * <p>
     * オブジェクトのtoStringメソッドを上記のようにすると {@code Test[id=0, name=test]} と表示される
     * </p>
     * 
     * @param object
     * @return オブジェクト名及びオブジェクトの中身
     * @since 0.3.7
     */
    public static final String toString(Object object) {
        if (Objects.isNull(object)) {
            throw new NullPointerException("オブジェクトがnullです");
        }

        final Map<String, Object> map = toMap(object);
        StringBuffer buffer = new StringBuffer();

        buffer.append(object.getClass().getSimpleName() + "[");

        for (Map.Entry<String, Object> field : map.entrySet()) {
            final String str = field.getKey() + "=" + field.getValue();
            buffer.append(str + ", ");
        }

        final String result = buffer.toString().replaceAll(",\\s$", "");

        return result.concat("]");
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
        // 念のためthis$0とserialVersionUIDを除外しておく
        return Arrays.asList(object.getClass().getDeclaredFields()).stream().map(Field::getName)
                .filter(name -> !Arrays.asList(excludes).contains(name) && !name.matches("this\\$0|serialVersionUID"))
                .collect(Collectors.toList());
    }

    /**
     * オブジェクトの中身のMapを返却する
     * 
     * @param object   オブジェクト
     * @param excludes 除外フィールド名のString配列
     * @return 変数名:パラメータのマップ
     * @since 0.3.7
     */
    public static final Map<String, Object> toMap(Object object, String... excludes) {
        Map<String, Object> fieldNames = new HashMap<>();
        final List<Field> fields = Arrays.asList(object.getClass().getDeclaredFields());

        for (Field field : fields) {
            try {
                field.setAccessible(true);
                final String name = field.getName();
                final Object param = field.get(object);
                if (!Arrays.asList(excludes).contains(name) && !name.matches("this\\$0|serialVersionUID")) {
                    fieldNames.put(name, param);
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return fieldNames;
    }

    /**
     * オブジェクトの中身のMapを返却する
     * 
     * @param object オブジェクト
     * @return 変数名:パラメータのマップ
     * @since 0.3.8
     */
    public static final Map<String, Object> toMap(Object object) {
        Map<String, Object> fieldNames = new HashMap<>();
        final List<Field> fields = Arrays.asList(object.getClass().getDeclaredFields());

        for (Field field : fields) {
            try {
                field.setAccessible(true);
                final String name = field.getName();
                final Object param = field.get(object);
                if (!name.matches("this\\$0|serialVersionUID")) {
                    fieldNames.put(name, param);
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return fieldNames;
    }

    /**
     * 指定した2つのオブジェクトから同一のフィールドリストを返却する
     * 
     * @param a        オブジェクトa
     * @param b        オブジェクトb
     * @param excludes 除外フィールド名のString配列
     * @return 同一フィールドリスト
     * @throws NullPointerException オブジェクトaまたはオブジェクトbが {@code null} の場合にthrowする
     * @since 0.3.4
     */
    public static final List<String> getSameFieldNames(Object a, Object b, String... excludes) {
        checkObjectNull(a, b);

        final List<String> A = getFieldNames(a, excludes);
        final List<String> B = getFieldNames(b, excludes);

        return getSameFieldNames(A, B);
    }

    /**
     * 指定した2つのフィールドリストから同一のフィールドリストを返却する
     * 
     * @param a オブジェクトaのフィールドリスト
     * @param b オブジェクトbのフィールドリスト
     * @return 同一フィールドリスト
     * @since 0.1.8
     */
    public static final List<String> getSameFieldNames(List<String> a, List<String> b) {
        return a.stream().filter(name -> b.contains(name)).collect(Collectors.toList());
    }

    /**
     * オブジェクト比較リソースクラス
     * 
     * @author gn5r
     * @since 0.1.2-RELEASE
     */
    public static class Difference {

        private String fieldName;
        private Object a;
        private Object b;

        /**
         * 引数なしのコンストラクタ
         */
        public Difference() {

        }

        /**
         * 引数ありのコンストラクタ
         * 
         * @param fieldName フィールド名(変数名)
         * @param a         オブジェクトaのフィールドパラメータ
         * @param b         オブジェクトbのフィールドパラメータ
         */
        public Difference(final String fieldName, final Object a, final Object b) {
            this.fieldName = fieldName;
            this.a = a;
            this.b = b;
        }

        /**
         * フィールド名をセットする
         * 
         * @param fieldName フィールド名(変数名)
         */
        public final void setFieldName(final String fieldName) {
            this.fieldName = fieldName;
        }

        /**
         * オブジェクトaのフィールドパラメータをセットする
         * 
         * @param a フィールドパラメータ
         */
        public final void setLeft(final String a) {
            this.a = a;
        }

        /**
         * オブジェクトbのフィールドパラメータをセットする
         * 
         * @param b フィールドパラメータ
         */
        public final void setRight(final String b) {
            this.b = b;
        }

        /**
         * フィールド名(変数名)を取得する
         * 
         * @return フィールド名(変数名)
         */
        public final String getFieldName() {
            return this.fieldName;
        }

        /**
         * オブジェクトaのフィールドパラメータを取得する
         * 
         * @return フィールドパラメータ
         */
        public final Object getA() {
            return this.a;
        }

        /**
         * オブジェクトbのフィールドパラメータを取得する
         * 
         * @return フィールドパラメータ
         */
        public final Object getB() {
            return this.b;
        }
    }
}