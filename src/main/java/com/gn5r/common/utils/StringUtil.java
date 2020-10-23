package com.gn5r.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * {@link StringUtils}拡張クラス
 * </p>
 *
 * <ul>
 * <li><b>substring</b> - 正規表現にマッチした文字列を切り出す。マッチしなければ {@code null} を返却</li>
 * <li><b>nullToEmpty</b> - 引数で渡された {@link CharSequence} が {@code null} だった場合
 * 空文字("") を返却する</li>
 * </ul>
 *
 * @author gn5r
 * @since 0.1.0-RELEASE
 * @see StringUtils
 */
public final class StringUtil extends StringUtils {

	/**
	 * 日本語デフォルトエンコーディング
	 * 
	 * @since 0.3.3
	 */
	public static final String DEFAULT_ENCODING = "MS932";

	/**
	 * 正規表現にマッチした最初の文字列を切り取る。マッチしなければ {@code null} を返却
	 *
	 * @param str   切り出し元文字列
	 * @param regex 正規表現
	 * @return マッチした文字列または {@code null}
	 * @since 0.1.0-RELEASE
	 */
	public static final String substring(final String str, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);

		if (m.find()) {
			return m.group();
		} else {
			return null;
		}
	}

	/**
	 * 指定した文字列の先頭から指定したバイト数分切り出す
	 * <p>
	 * 指定した文字列が {@code null} または 空文字("") だった場合は空文字("")を返却する
	 * </p>
	 * <p>
	 * バイト数取得時のエンコーディングは<b>MS932</b>
	 * </p>
	 * 
	 * @param str    文字列
	 * @param length 切り出したいバイト数
	 * @return 切り出した文字列
	 * @since 0.3.3
	 */
	public static final String substring(final String str, final int length) {
		if (Objects.isNull(str)) {
			return EMPTY;
		}

		if (isEmpty(str)) {
			return EMPTY;
		}

		String ret = "";

		int cnt = 0;

		for (int i = 0; i < str.length(); i++) {
			String tmp = str.substring(i, i + 1);
			final int byteLength = getByteLength(tmp);

			if (cnt + byteLength > length) {
				return ret;
			} else {
				ret = ret.concat(tmp);
				cnt += byteLength;
			}
		}

		return ret;
	}

	/**
	 * 指定した文字列の先頭から指定したバイト数分切り出す
	 * <p>
	 * 指定した文字列が {@code null} または 空文字("") だった場合は空文字("")を返却する
	 * </p>
	 * <p>
	 * バイト数取得時のデフォルトエンコーディングは<b>MS932</b>
	 * </p>
	 * 
	 * @param str      文字列
	 * @param length   切り出したいバイト数
	 * @param encoding エンコーディング
	 * @return 切り出した文字列
	 * @since 0.3.3
	 */
	public static final String substring(final String str, final int length, final String encoding) {
		if (Objects.isNull(str)) {
			return EMPTY;
		}

		if (isEmpty(str)) {
			return EMPTY;
		}

		String ret = "";

		int cnt = 0;

		for (int i = 0; i < str.length(); i++) {
			String tmp = str.substring(i, i + 1);
			final int byteLength = getByteLength(tmp, encoding);

			if (cnt + byteLength > length) {
				return ret;
			} else {
				ret = ret.concat(tmp);
				cnt += byteLength;
			}
		}

		return ret;
	}

	/**
	 * <p>
	 * 引数で渡された {@link CharSequence} が null だった場合に 空文字("") を返却する
	 * </p>
	 *
	 * @param cs チェックしたいCharSequence
	 * @return 最初の引数が {@code null} であれば 空文字("") を返却し、それ以外の場合は文字列に変換したものを返却
	 * @see java.util.Objects#toString(Object, String) Objects#toString(Object,
	 *      String)
	 * @since 0.1.0-RELEASE
	 */
	public static final String nullToEmpty(final CharSequence cs) {
		return cs == null ? EMPTY : cs.toString();
	}

	/**
	 * 指定した文字列のバイト数を取得する
	 * <p>
	 * 指定した文字列が {@code null} または 空文字("") だった場合は0を返却する
	 * </p>
	 * <p>
	 * バイト数取得時のデフォルトエンコーディングは<b>MS932</b>
	 * </p>
	 * 
	 * @param str      文字列
	 * @param encoding エンコーディング
	 * @return バイト数
	 * @since 0.3.3
	 */
	public static final int getByteLength(final String str, final String encoding) {
		if (Objects.isNull(str)) {
			return 0;
		}

		if (isEmpty(str)) {
			return 0;
		}

		try {
			return str.getBytes(isEmpty(encoding) ? DEFAULT_ENCODING : encoding).length;
		} catch (UnsupportedEncodingException e) {
			return 0;
		}
	}

	/**
	 * 指定した文字列のバイト数を取得する
	 * <p>
	 * 指定した文字列が {@code null} または 空文字("") だった場合は0を返却する
	 * </p>
	 * <p>
	 * バイト数取得時のエンコーディングは<b>MS932</b>
	 * </p>
	 * 
	 * @param str      文字列
	 * @return バイト数
	 * @since 0.3.3
	 */
	public static final int getByteLength(final String str) {
		if (Objects.isNull(str)) {
			return 0;
		}

		if (isEmpty(str)) {
			return 0;
		}

		try {
			return str.getBytes(DEFAULT_ENCODING).length;
		} catch (UnsupportedEncodingException e) {
			return 0;
		}
	}
}