<#ftl encoding='UTF-8'>
package ${packagePath};

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

// 消息管理器
public class ${handlerClassName} {
	private final List<DSyncListener> listeners = new ArrayList<>();

	public void addListener(DSyncListener listener) {
		listeners.add(listener);
	}
	
	public void removeListener(DSyncListener listener) {
		listeners.remove(listener);
	}

	public void clearListener(DSyncListener listener) {
	    listeners.clear();
	}
	
	// 消息监听接口
	public static interface DSyncListener {
<#list structs as struct>
<#if struct.isMsg>

		default boolean onMessage(${handlerClassName} handler, ${struct.className} msg) {
			return false;
		}
</#if>
</#list>
	}
	
	// 接受到消息，解析并派发给监听者
	public boolean onMessage(String string, boolean once) {
		boolean handle = false;
<#if hasMsg>
		JSONObject jsonObject = JSONObject.parseObject(string);
		String typeName = jsonObject.getString(DSyncBase.K.TypeName);
		switch (typeName) {
<#list structs as struct>
<#if struct.isMsg>
		case ${struct.className}.TypeName:
			for (DSyncListener listener : listeners) {
				handle |= listener.onMessage(this, ${struct.className}.parseJSONObject(jsonObject));
				if (once && handle) {
					return true;
				}
			}
			break;
</#if>
</#list>
		default:
			break;
		}
</#if>
		return handle;
	}
	
	public static DSyncBase parseStruct(String string) {
		JSONObject jsonObject = JSONObject.parseObject(string);
		String typeName = jsonObject.getString(DSyncBase.K.TypeName);
		switch (typeName) {
<#list structs as struct>
		case ${struct.className}.TypeName:
			return ${struct.className}.parseJSONObject(jsonObject);
</#list>
		}
		return null;
	}

	protected static DSyncBase newDSyncInstance(String typeName) {
		switch (typeName) {
<#list structs as struct>
		case ${struct.className}.TypeName:
			return new ${struct.className}();
</#list>
		default:
			return null;
		}
	}

<#list structs as struct>
<#if struct.hasComment>
	/**
<#list struct.comments as comment>
	 * ${comment}
</#list>
	 */
</#if>
	public static class ${struct.className} extends DSyncBase {
		public static final String TypeName = "${struct.className}";
		
<#list struct.fields as field>
<#if field.hasComment>
		/** ${field.comment} */
</#if>
		private ${field.typeName} ${field.name};
</#list>

		public static class K {
<#list struct.fields as field>
			public static final String ${field.name} = "${field.name}";
</#list>
		}

		public ${struct.className}() {
			init();
		}

		@Override
		protected void init() {
<#list struct.fields as field>
			${field.name} = ${field.defaultValue};
</#list>
		}
		
<#list struct.fields as field>
<#if field.array>
<#if field.hasComment>
		/** ${field.comment} */
</#if>
		public int get${field.largeName}Count() {
			return this.${field.name}.size();
		}
		
<#if field.hasComment>
		/** ${field.comment} */
</#if>
		public ${field.typeName} get${field.largeName}List() {
			return new ArrayList<>(${field.name});
		}
		
<#if field.hasComment>
		/** ${field.comment} */
</#if>
		public void set${field.largeName}List(${field.typeName} ${field.name}) {
			this.${field.name}.clear();
			this.${field.name}.addAll(${field.name});
		}

<#if field.hasComment>
		/** ${field.comment} */
</#if>
		public void add${field.largeName}(${field.singleTypeName} ${field.name}) {
			this.${field.name}.add(${field.name});
		}
		
<#if field.hasComment>
		/** ${field.comment} */
</#if>
		public void addAll${field.largeName}(${field.typeName} ${field.name}) {
			this.${field.name}.addAll(${field.name});
		}
		
<#if field.hasComment>
		/** ${field.comment} */
</#if>
		public void clear${field.largeName}() {
			this.${field.name}.clear();
		}
		
<#else>
<#if field.hasComment>
		/** ${field.comment} */
</#if>
		public ${field.typeName} get${field.largeName}() {
			return ${field.name};
		}
		
<#if field.hasComment>
		/** ${field.comment} */
</#if>
		public void set${field.largeName}(${field.typeName} ${field.name}) {
			this.${field.name} = ${field.name};
		}
		
</#if>
</#list>

		public static ${struct.className} parseJSONObject(JSONObject jsonObject) {
			${struct.className} _value = new ${struct.className}();
			if (!jsonObject.isEmpty()) {
				_value.applyRecord(jsonObject);
			}
			return _value;
		}
		
		public static List<${struct.className}> parseJSONArray(JSONArray jsonArray) {
			ArrayList<${struct.className}> list = new ArrayList<${struct.className}>();
			for (int i = 0; i < jsonArray.size(); i++) {
				${struct.className} _value = new ${struct.className}();
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				if (!jsonObject.isEmpty()) {
					_value.applyRecord(jsonObject);
				}
				list.add(_value);
			}
			return list;
		}

		@Override
		public void getRecord(JSONObject jsonObject) {
			jsonObject.put(DSyncBase.K.TypeName, TypeName);
<#list struct.fields as field>
<#if field.array>
<#if field.baseType>
			jsonObject.put(K.${field.name}, JSONObject.toJSONString(${field.name}));
<#else>
			jsonObject.put(K.${field.name}, getJSONArray(${field.name}));
</#if>
<#else>
<#if field.baseType>
			jsonObject.put(K.${field.name}, ${field.name});
<#elseif field.enumType>
			jsonObject.put(K.${field.name}, ${field.name}.ordinal());
<#else>
			jsonObject.put(K.${field.name}, getJSONObject(${field.name}));
</#if>
</#if>
</#list>
		}

		@Override
		public void applyRecord(JSONObject jsonObject) {
<#list struct.fields as field>
<#if field.array>
<#if field.baseType>
			${field.name} = JSONObject.parseArray(jsonObject.getString(K.${field.name}), ${field.largeTypeName}.class);
<#else>
			${field.name} = ${field.largeTypeName}.parseJSONArray(jsonObject.getJSONArray(K.${field.name}));
</#if>
<#else>	
<#if field.baseType>
			${field.name} = jsonObject.get${field.longTypeName}(K.${field.name});
<#elseif field.enumType>
			${field.name} = ${field.typeName}.values()[(jsonObject.getIntValue(K.${field.name}))];
<#else>
			${field.name} = ${field.largeTypeName}.parseJSONObject(jsonObject.getJSONObject(K.${field.name}));
</#if>
</#if>
</#list>
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof ${struct.className})) {
				return false;
			}
<#if (struct.fields)?size != 0>
			${struct.className} _value = (${struct.className}) obj;
</#if>
<#list struct.fields as field>
<#if field.equalType>
			if (!this.${field.name}.equals(_value.${field.name})) {
				return false;
			}
<#else>
			if (this.${field.name} != _value.${field.name}) {
				return false;
			}
</#if>
</#list>
			return true;
		}
		
		public ${struct.className} copy() {
			${struct.className} _value = new ${struct.className}();
<#list struct.fields as field>
<#if field.array>
			_value.${field.name} = new ArrayList<>(this.${field.name});
<#else>
			_value.${field.name} = this.${field.name};
</#if>
</#list>
			return _value;
		}
		
		public ${struct.className} deepCopy() {
			${struct.className} _value = new ${struct.className}();
<#list struct.fields as field>
<#if field.array>
<#if field.copyType>
			_value.${field.name} = new ArrayList<>();
			for(${field.largeTypeName} _f: this.${field.name}) {
				if (_f != null) {
					_value.${field.name}.add(_f.deepCopy());
				} else {
					_value.${field.name}.add(null);
				}
			}
<#else>
			_value.${field.name} = new ArrayList<>(this.${field.name});
</#if>
<#else>
<#if field.copyType>
			if (this.${field.name} != null) {
				_value.${field.name} = this.${field.name}.deepCopy();
			}
<#else>
			_value.${field.name} = this.${field.name};
</#if>
</#if>
</#list>
			return _value;
		}
	}
	
</#list>

<#list enums as struct>
<#if struct.hasComment>
	/**
<#list struct.comments as comment>
	 * ${comment}
</#list>
	 */
</#if>
	public static enum ${struct.className} {
<#list struct.fields as field>
<#if field.hasComment>
		/** ${field.comment} */
</#if>
		${field.name},
</#list>
	}
</#list>


	public static abstract class DSyncBase {
		public static class K {
			public static final String TypeName = "_TypeName";
		}

		protected void init() {
		}

		public final <T extends DSyncBase> List<T> fromJSONString(String string) {
			return null;
		}

		protected void getRecord(JSONObject jsonObject) {
		}

		protected void applyRecord(JSONObject jsonObject) {
		}

		public static <T extends DSyncBase> JSONArray getJSONArray(List<T> objs) {
			JSONArray array = new JSONArray();
			for (T obj : objs) {
				JSONObject jsonObj = new JSONObject();
				if (obj != null) {
					obj.getRecord(jsonObj);
				}
				array.add(jsonObj);
			}
			return array;
		}

		public static JSONObject getJSONObject(DSyncBase base) {
			JSONObject jsonObj = new JSONObject();
			if (base != null) {
				base.getRecord(jsonObj);
			}
			return jsonObj;
		}

		public final String toMessage() {
			JSONObject json = new JSONObject();
			getRecord(json);
			return json.toJSONString();
		}

		public String getTypeName() {
			return "_Base";
		}
	}
}

