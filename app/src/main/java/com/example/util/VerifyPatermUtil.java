package com.example.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerifyPatermUtil {
	// wi =2(n-1)(mod 11);加权因子
	final static int[] wi = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 };
	// 校验码
	final static int[] vi = { 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 };
	private static int[] ai = new int[18];

	// 校验身份证的校验码
	public static Map<String, Object> verifyIdCard(String idcard) {
		Map<String, Object> pMap = new HashMap<String, Object>();
		if (StringUtils.isEmpty(idcard)) {
			pMap.put("B", false);
			pMap.put("R", "身份证号为空");
			return pMap;
		}
		if (idcard.length() == 15) {
			idcard = uptoeighteen(idcard);
		}
		if (idcard.length() != 18) {
			pMap.put("B", false);
			pMap.put("R", "身份证号不足18位");
			return pMap;
		}
		String verify = idcard.substring(17, 18);
		if (verify.equals(getVerify(idcard))) {
			pMap.put("B", true);
			pMap.put("R", " ");
			return pMap;
		} else {
			pMap.put("B", false);
			pMap.put("R", "身份证号不符合规范");
		}
		return pMap;
	}

	// 15位转18位
	public static String uptoeighteen(String fifteen) {
		StringBuffer eighteen = new StringBuffer(fifteen);
		eighteen = eighteen.insert(6, "19");
		return eighteen.toString();
	}

	// 计算最后一位校验值
	public static String getVerify(String eighteen) {
		int remain = 0;
		if (eighteen.length() == 18) {
			eighteen = eighteen.substring(0, 17);
		}
		if (eighteen.length() == 17) {
			int sum = 0;
			for (int i = 0; i < 17; i++) {
				String k = eighteen.substring(i, i + 1);
				ai[i] = Integer.valueOf(k);
			}
			for (int i = 0; i < 17; i++) {
				sum += wi[i] * ai[i];
			}
			remain = sum % 11;
		}
		return remain == 2 ? "X" : String.valueOf(vi[remain]);

	}

	// --------------手机号验证
	public static Map<String, Object> verifyMobileNO(String mobiles) {
		Map<String, Object> pMap = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotEmpty(mobiles)) {
				Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
				Matcher m = p.matcher(mobiles);
				if (m.matches()) {
					pMap.put("B", true);
					pMap.put("R", " ");
				} else {
					pMap.put("B", false);
					pMap.put("R", "手机号码不符合规范");
				}
			} else {
				pMap.put("B", false);
				pMap.put("R", "手机号码为空");
			}
		} catch (Exception e) {
			pMap.put("B", false);
			pMap.put("R", "手机验证匹配发生异常");
		}
		return pMap;
	}

	public static Map<String, Object> verifyMoileCode(String number) {
		Map<String, Object> pMap = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotEmpty(number)) {
				Pattern p = Pattern.compile("^[0-9]{6}$");
				Matcher m = p.matcher(number);
				if (m.matches()) {
					pMap.put("B", true);
					pMap.put("R", " ");
				} else {
					pMap.put("B", false);
					pMap.put("R", "手机验证码不符合规范");
				}
			} else {
				pMap.put("B", false);
				pMap.put("R", "手机验证码为空");
			}
		} catch (Exception e) {
			pMap.put("B", false);
			pMap.put("R", "手机验证码匹配发生异常");
		}
		return pMap;
	}

	public static Map<String, Object> verifyActiveCode(String code) {
		Map<String, Object> pMap = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotEmpty(code)) {
				if (code.length() == 8) {
					Pattern p = Pattern.compile("^[a-zA-Z0-9]+$");
					Matcher m = p.matcher(code);
					if (m.matches()) {
						pMap.put("B", true);
						pMap.put("R", " ");
					} else {
						pMap.put("B", false);
						pMap.put("R", "激活码不符合规范");
					}
				} else {
					pMap.put("B", false);
					pMap.put("R", "激活码长度不为8位");
				}
			} else {
				pMap.put("B", false);
				pMap.put("R", "激活码为空");
			}
		} catch (Exception e) {
			pMap.put("B", false);
			pMap.put("R", "验证激活码发生异常");
		}
		return pMap;
	}

	public static Map<String, Object> verifyValidateCode(String code) {
		Map<String, Object> pMap = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotEmpty(code)) {
				Pattern p = Pattern.compile("^[0-9]{4}$");
				Matcher m = p.matcher(code);
				if (m.matches()) {
					pMap.put("B", true);
					pMap.put("R", " ");
				} else {
					pMap.put("B", false);
					pMap.put("R", "验证码不符合规范");
				}
			} else {
				pMap.put("B", false);
				pMap.put("R", "验证码为空");
			}
		} catch (Exception e) {
			pMap.put("B", false);
			pMap.put("R", "验证验证码是发生异常");
		}
		return pMap;
	}

	public static Map<String, Object> verify2Password(String psd1, String psd2) {
		Map<String, Object> pMap = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotEmpty(psd1) && StringUtils.isNotEmpty(psd2)) {
				if ((psd1.length() >= 6) && (psd2.length() >= 6)) {
					if ((psd1.length() <= 24) && (psd2.length() <= 24)) {
						Pattern p = Pattern.compile("^[a-zA-Z0-9!@#$%^&*_]+$");
						Matcher m1 = p.matcher(psd1);
						Matcher m2 = p.matcher(psd2);
						if (m1.matches() && m2.matches()) {
							if (psd1.equals(psd2)) {
								pMap.put("B", true);
								pMap.put("R", " ");
							} else {
								pMap.put("B", false);
								pMap.put("R", "两次密码不一致");
							}
						} else {
							pMap.put("B", false);
							pMap.put("R", "密码不符合规范");
						}
					} else {
						pMap.put("B", false);
						pMap.put("R", "有密码大于24位");
					}
				} else {
					pMap.put("B", false);
					pMap.put("R", "有密码小于6位");
				}
			} else {
				pMap.put("B", false);
				pMap.put("R", "登录密码不能为空");
			}
		} catch (Exception e) {

		}
		return pMap;
	}

	public static Map<String, Object> verifyPaypsdOld(String psd) {
		Map<String, Object> pMap = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotEmpty(psd)) {
				if ((psd.length() == 6)) {
					Pattern p = Pattern.compile("^[0-9]*$");
					Matcher m = p.matcher(psd);
					if (m.matches()) {
						pMap.put("B", true);
						pMap.put("R", " ");
					} else {
						pMap.put("B", false);
						pMap.put("R", "旧密码不符合规范");
					}
				} else {
					pMap.put("B", false);
					pMap.put("R", "旧支付密码不为6位");
				}
			} else {
				pMap.put("B", false);
				pMap.put("R", "旧支付密码为空");
			}
		} catch (Exception e) {

		}
		return pMap;
	}

	public static Map<String, Object> verify2Paypsd(String psd1, String psd2) {
		Map<String, Object> pMap = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotEmpty(psd1) && StringUtils.isNotEmpty(psd2)) {
				if ((psd1.length() == 6) && (psd2.length() == 6)) {
					Pattern p = Pattern.compile("^[0-9]*$");
					Matcher m1 = p.matcher(psd1);
					Matcher m2 = p.matcher(psd2);
					if (m1.matches() && m2.matches()) {
						if (psd1.equals(psd2)) {
							pMap.put("B", true);
							pMap.put("R", " ");
						} else {
							pMap.put("B", false);
							pMap.put("R", "两次密码不一致");
						}
					} else {
						pMap.put("B", false);
						pMap.put("R", "密码不符合规范");
					}
				} else {
					pMap.put("B", false);
					pMap.put("R", "有支付密码不为6位");
				}
			} else {
				pMap.put("B", false);
				pMap.put("R", "支付密码不能为空");
			}
		} catch (Exception e) {

		}
		return pMap;
	}

	public static Map<String, Object> verifyPassword(String psd) {
		Map<String, Object> pMap = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotEmpty(psd)) {
				if (psd.length() >= 6) {
					if (psd.length() <= 24) {
						Pattern p = Pattern.compile("^[a-zA-Z0-9!@#$%^&*_]+$");
						Matcher m = p.matcher(psd);
						if (m.matches()) {
							pMap.put("B", true);
							pMap.put("R", " ");
						} else {
							pMap.put("B", false);
							pMap.put("R", "密码不符合规范");
						}
					} else {
						pMap.put("B", false);
						pMap.put("R", "密码大于24位");
					}
				} else {
					pMap.put("B", false);
					pMap.put("R", "密码小于6位");
				}
			} else {
				pMap.put("B", false);
				pMap.put("R", "密码为空");
			}
		} catch (Exception e) {
			pMap.put("B", false);
			pMap.put("R", "验证密码时发生错误");
		}
		return pMap;
	}

	public static Map<String, Object> verifyAccount(String account) {
		Map<String, Object> pMap = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotEmpty(account)) {
				if (account.length() >= 6) {
					if (account.length() <= 24) {
						Pattern p = Pattern.compile("^[a-zA-Z0-9]+$");
						Matcher m = p.matcher(account);
						if (m.matches()) {
							pMap.put("B", true);
							pMap.put("R", " ");
						} else {
							pMap.put("B", false);
							pMap.put("R", "用户名不符合规范");
						}
					} else {
						pMap.put("B", false);
						pMap.put("R", "用户名大于24位");
					}
				} else {
					pMap.put("B", false);
					pMap.put("R", "用户名小于6位");
				}
			} else {
				pMap.put("B", false);
				pMap.put("R", "用户名为空");
			}
		} catch (Exception e) {
			pMap.put("B", false);
			pMap.put("R", "验证用户名时发生错误");
		}
		return pMap;
	}

	public static Map<String, Object> verifyEmail(String email) {
		Map<String, Object> eMap = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotEmpty(email)) {
				Pattern p = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
				Matcher m = p.matcher(email);
				if (m.matches()) {
					eMap.put("B", true);
					eMap.put("R", " ");
				} else {
					eMap.put("B", false);
					eMap.put("R", "邮箱不符合规范");
				}
			} else {
				eMap.put("B", false);
				eMap.put("R", "邮箱为空");
			}

		} catch (Exception e) {
			eMap.put("B", false);
			eMap.put("R", "验证邮箱时发生错误");
		}
		return eMap;
	}
}
