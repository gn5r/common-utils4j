package com.gn5r.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>{@link StringUtils}拡張クラス</p>
 *
 * <ul>
 *   <li><b>substring</b>
 *    - 正規表現にマッチした文字列を切り出す。マッチしなければ {@code null} を返却</li>
 *   <li><b>nullToEmpty</b>
 *    - 引数で渡された {@link CharSequence} が {@code null} だった場合 空文字("") を返却する</li>
 * </ul>
 *
 * @author gn5r
 * @since 0.1.0-RELEASE
 * @see StringUtils
 */
public final class StringUtil extends StringUtils {

	/**
	 * 正規表現にマッチした最初の文字列を切り取る。マッチしなければ {@code null} を返却
	 *
	 * @param str 切り出し元文字列
	 * @param regex 正規表現
	 * @return マッチした文字列または {@code null}
	 * @since 0.1.0
	 */
	public static String substring(final String str, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);

		if (m.find()) {
			return m.group();
		} else {
			return null;
		}
	}

	/**
	 * <p>引数で渡された {@link CharSequence} が null だった場合に 空文字("") を返却する</p>
	 *
	 * @param cs チェックしたいCharSequence
	 * @return 最初の引数が {@code null} であれば 空文字("") を返却し、それ以外の場合は文字列に変換したものを返却
	 * @see java.util.Objects#toString(Object, String) Objects#toString(Object, String)
	 * @since 0.1.0
	 */
	public static String nullToEmpty(final CharSequence cs) {
		return cs == null ? EMPTY : cs.toString();
	}
}