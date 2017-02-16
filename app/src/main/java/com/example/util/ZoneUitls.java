package com.example.util;

public class ZoneUitls {
	public static String getZoneString(String type) {
		String result = "";
		if (StringUtils.isEmpty(type)) {
			return result;
		} else {
			for (int i = 0; i < Zone_MapTable.length; i++) {
				if (type.equals(Zone_MapTable[i][0])) {
					result = Zone_MapTable[i][1];
				}
			}
			if (StringUtils.isNotEmpty(result)) {
				return result;
			} else {
				return "";
			}
		}
	}
	
	private static final String[][] Zone_MapTable = {
		// {类型， 类型解释}
		{"510182","彭州市"},
		{"510114","新都区"},
		{"510121","金堂县"},
		{"510183","邛崃市"},
		{"510132","新津县"},
		{"510105","青羊区"},
		{"510108","成华区"},
		{"510113","青白江区"},
		{"510124","郫县"},
		{"510106","金牛区"},
		{"510107","武侯区"},
		{"510104","锦江区"},
		{"510115","温江区"},
		{"510184","崇州市"},
		{"510181","都江堰市"},
		{"510122","双流县"},
		{"510131","蒲江县"},
		{"510129","大邑县"},
		{"510112","龙泉驿区"}
	};
}
