package com.backinfile.portal.msg;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

// 消息管理器
public class GameMsgHandler {
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

		default boolean onMessage(GameMsgHandler handler, SCHumanUpdate msg) {
			return false;
		}

		default boolean onMessage(GameMsgHandler handler, SCBoardInit msg) {
			return false;
		}

		default boolean onMessage(GameMsgHandler handler, SCCardsMove msg) {
			return false;
		}
	}
	
	// 接受到消息，解析并派发给监听者
	public boolean onMessage(String string, boolean once) {
		boolean handle = false;
		JSONObject jsonObject = JSONObject.parseObject(string);
		String typeName = jsonObject.getString(DSyncBase.K.TypeName);
		switch (typeName) {
		case SCHumanUpdate.TypeName:
			for (DSyncListener listener : listeners) {
				handle |= listener.onMessage(this, SCHumanUpdate.parseJSONObject(jsonObject));
				if (once && handle) {
					return true;
				}
			}
			break;
		case SCBoardInit.TypeName:
			for (DSyncListener listener : listeners) {
				handle |= listener.onMessage(this, SCBoardInit.parseJSONObject(jsonObject));
				if (once && handle) {
					return true;
				}
			}
			break;
		case SCCardsMove.TypeName:
			for (DSyncListener listener : listeners) {
				handle |= listener.onMessage(this, SCCardsMove.parseJSONObject(jsonObject));
				if (once && handle) {
					return true;
				}
			}
			break;
		default:
			break;
		}
		return handle;
	}
	
	public static DSyncBase parseStruct(String string) {
		JSONObject jsonObject = JSONObject.parseObject(string);
		String typeName = jsonObject.getString(DSyncBase.K.TypeName);
		switch (typeName) {
		case SCHumanUpdate.TypeName:
			return SCHumanUpdate.parseJSONObject(jsonObject);
		case SCBoardInit.TypeName:
			return SCBoardInit.parseJSONObject(jsonObject);
		case SCCardsMove.TypeName:
			return SCCardsMove.parseJSONObject(jsonObject);
		case DCard.TypeName:
			return DCard.parseJSONObject(jsonObject);
		case DCardPosition.TypeName:
			return DCardPosition.parseJSONObject(jsonObject);
		case DHuman.TypeName:
			return DHuman.parseJSONObject(jsonObject);
		}
		return null;
	}

	protected static DSyncBase newDSyncInstance(String typeName) {
		switch (typeName) {
		case SCHumanUpdate.TypeName:
			return new SCHumanUpdate();
		case SCBoardInit.TypeName:
			return new SCBoardInit();
		case SCCardsMove.TypeName:
			return new SCCardsMove();
		case DCard.TypeName:
			return new DCard();
		case DCardPosition.TypeName:
			return new DCardPosition();
		case DHuman.TypeName:
			return new DHuman();
		default:
			return null;
		}
	}

	public static class SCHumanUpdate extends DSyncBase {
		public static final String TypeName = "SCHumanUpdate";
		
		private List<DHuman> humans;

		public static class K {
			public static final String humans = "humans";
		}

		public SCHumanUpdate() {
			init();
		}

		@Override
		protected void init() {
			humans = new ArrayList<>();
		}
		
		public int getHumansCount() {
			return this.humans.size();
		}
		
		public List<DHuman> getHumansList() {
			return new ArrayList<>(humans);
		}
		
		public void setHumansList(List<DHuman> humans) {
			this.humans.clear();
			this.humans.addAll(humans);
		}

		public void addHumans(DHuman humans) {
			this.humans.add(humans);
		}
		
		public void addAllHumans(List<DHuman> humans) {
			this.humans.addAll(humans);
		}
		
		public void clearHumans() {
			this.humans.clear();
		}
		

		public static SCHumanUpdate parseJSONObject(JSONObject jsonObject) {
			SCHumanUpdate _value = new SCHumanUpdate();
			if (!jsonObject.isEmpty()) {
				_value.applyRecord(jsonObject);
			}
			return _value;
		}
		
		public static List<SCHumanUpdate> parseJSONArray(JSONArray jsonArray) {
			ArrayList<SCHumanUpdate> list = new ArrayList<SCHumanUpdate>();
			for (int i = 0; i < jsonArray.size(); i++) {
				SCHumanUpdate _value = new SCHumanUpdate();
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
			jsonObject.put(K.humans, getJSONArray(humans));
		}

		@Override
		public void applyRecord(JSONObject jsonObject) {
			humans = DHuman.parseJSONArray(jsonObject.getJSONArray(K.humans));
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof SCHumanUpdate)) {
				return false;
			}
			SCHumanUpdate _value = (SCHumanUpdate) obj;
			if (!this.humans.equals(_value.humans)) {
				return false;
			}
			return true;
		}
		
		public SCHumanUpdate copy() {
			SCHumanUpdate _value = new SCHumanUpdate();
			_value.humans = new ArrayList<>(this.humans);
			return _value;
		}
		
		public SCHumanUpdate deepCopy() {
			SCHumanUpdate _value = new SCHumanUpdate();
			_value.humans = new ArrayList<>();
			for(DHuman _f: this.humans) {
				if (_f != null) {
					_value.humans.add(_f.deepCopy());
				} else {
					_value.humans.add(null);
				}
			}
			return _value;
		}
	}
	
	public static class SCBoardInit extends DSyncBase {
		public static final String TypeName = "SCBoardInit";
		
		private SCHumanUpdate humans;
		private SCCardsMove cards;

		public static class K {
			public static final String humans = "humans";
			public static final String cards = "cards";
		}

		public SCBoardInit() {
			init();
		}

		@Override
		protected void init() {
			humans = null;
			cards = null;
		}
		
		public SCHumanUpdate getHumans() {
			return humans;
		}
		
		public void setHumans(SCHumanUpdate humans) {
			this.humans = humans;
		}
		
		public SCCardsMove getCards() {
			return cards;
		}
		
		public void setCards(SCCardsMove cards) {
			this.cards = cards;
		}
		

		public static SCBoardInit parseJSONObject(JSONObject jsonObject) {
			SCBoardInit _value = new SCBoardInit();
			if (!jsonObject.isEmpty()) {
				_value.applyRecord(jsonObject);
			}
			return _value;
		}
		
		public static List<SCBoardInit> parseJSONArray(JSONArray jsonArray) {
			ArrayList<SCBoardInit> list = new ArrayList<SCBoardInit>();
			for (int i = 0; i < jsonArray.size(); i++) {
				SCBoardInit _value = new SCBoardInit();
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
			jsonObject.put(K.humans, getJSONObject(humans));
			jsonObject.put(K.cards, getJSONObject(cards));
		}

		@Override
		public void applyRecord(JSONObject jsonObject) {
			humans = SCHumanUpdate.parseJSONObject(jsonObject.getJSONObject(K.humans));
			cards = SCCardsMove.parseJSONObject(jsonObject.getJSONObject(K.cards));
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof SCBoardInit)) {
				return false;
			}
			SCBoardInit _value = (SCBoardInit) obj;
			if (!this.humans.equals(_value.humans)) {
				return false;
			}
			if (!this.cards.equals(_value.cards)) {
				return false;
			}
			return true;
		}
		
		public SCBoardInit copy() {
			SCBoardInit _value = new SCBoardInit();
			_value.humans = this.humans;
			_value.cards = this.cards;
			return _value;
		}
		
		public SCBoardInit deepCopy() {
			SCBoardInit _value = new SCBoardInit();
			if (this.humans != null) {
				_value.humans = this.humans.deepCopy();
			}
			if (this.cards != null) {
				_value.cards = this.cards.deepCopy();
			}
			return _value;
		}
	}
	
	public static class SCCardsMove extends DSyncBase {
		public static final String TypeName = "SCCardsMove";
		
		private List<DCard> cards;

		public static class K {
			public static final String cards = "cards";
		}

		public SCCardsMove() {
			init();
		}

		@Override
		protected void init() {
			cards = new ArrayList<>();
		}
		
		public int getCardsCount() {
			return this.cards.size();
		}
		
		public List<DCard> getCardsList() {
			return new ArrayList<>(cards);
		}
		
		public void setCardsList(List<DCard> cards) {
			this.cards.clear();
			this.cards.addAll(cards);
		}

		public void addCards(DCard cards) {
			this.cards.add(cards);
		}
		
		public void addAllCards(List<DCard> cards) {
			this.cards.addAll(cards);
		}
		
		public void clearCards() {
			this.cards.clear();
		}
		

		public static SCCardsMove parseJSONObject(JSONObject jsonObject) {
			SCCardsMove _value = new SCCardsMove();
			if (!jsonObject.isEmpty()) {
				_value.applyRecord(jsonObject);
			}
			return _value;
		}
		
		public static List<SCCardsMove> parseJSONArray(JSONArray jsonArray) {
			ArrayList<SCCardsMove> list = new ArrayList<SCCardsMove>();
			for (int i = 0; i < jsonArray.size(); i++) {
				SCCardsMove _value = new SCCardsMove();
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
			jsonObject.put(K.cards, getJSONArray(cards));
		}

		@Override
		public void applyRecord(JSONObject jsonObject) {
			cards = DCard.parseJSONArray(jsonObject.getJSONArray(K.cards));
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof SCCardsMove)) {
				return false;
			}
			SCCardsMove _value = (SCCardsMove) obj;
			if (!this.cards.equals(_value.cards)) {
				return false;
			}
			return true;
		}
		
		public SCCardsMove copy() {
			SCCardsMove _value = new SCCardsMove();
			_value.cards = new ArrayList<>(this.cards);
			return _value;
		}
		
		public SCCardsMove deepCopy() {
			SCCardsMove _value = new SCCardsMove();
			_value.cards = new ArrayList<>();
			for(DCard _f: this.cards) {
				if (_f != null) {
					_value.cards.add(_f.deepCopy());
				} else {
					_value.cards.add(null);
				}
			}
			return _value;
		}
	}
	
	public static class DCard extends DSyncBase {
		public static final String TypeName = "DCard";
		
		private long id;
		private String sn;
		private DCardPosition position;

		public static class K {
			public static final String id = "id";
			public static final String sn = "sn";
			public static final String position = "position";
		}

		public DCard() {
			init();
		}

		@Override
		protected void init() {
			id = 0;
			sn = "";
			position = null;
		}
		
		public long getId() {
			return id;
		}
		
		public void setId(long id) {
			this.id = id;
		}
		
		public String getSn() {
			return sn;
		}
		
		public void setSn(String sn) {
			this.sn = sn;
		}
		
		public DCardPosition getPosition() {
			return position;
		}
		
		public void setPosition(DCardPosition position) {
			this.position = position;
		}
		

		public static DCard parseJSONObject(JSONObject jsonObject) {
			DCard _value = new DCard();
			if (!jsonObject.isEmpty()) {
				_value.applyRecord(jsonObject);
			}
			return _value;
		}
		
		public static List<DCard> parseJSONArray(JSONArray jsonArray) {
			ArrayList<DCard> list = new ArrayList<DCard>();
			for (int i = 0; i < jsonArray.size(); i++) {
				DCard _value = new DCard();
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
			jsonObject.put(K.id, id);
			jsonObject.put(K.sn, sn);
			jsonObject.put(K.position, getJSONObject(position));
		}

		@Override
		public void applyRecord(JSONObject jsonObject) {
			id = jsonObject.getLongValue(K.id);
			sn = jsonObject.getString(K.sn);
			position = DCardPosition.parseJSONObject(jsonObject.getJSONObject(K.position));
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof DCard)) {
				return false;
			}
			DCard _value = (DCard) obj;
			if (this.id != _value.id) {
				return false;
			}
			if (!this.sn.equals(_value.sn)) {
				return false;
			}
			if (!this.position.equals(_value.position)) {
				return false;
			}
			return true;
		}
		
		public DCard copy() {
			DCard _value = new DCard();
			_value.id = this.id;
			_value.sn = this.sn;
			_value.position = this.position;
			return _value;
		}
		
		public DCard deepCopy() {
			DCard _value = new DCard();
			_value.id = this.id;
			_value.sn = this.sn;
			if (this.position != null) {
				_value.position = this.position.deepCopy();
			}
			return _value;
		}
	}
	
	public static class DCardPosition extends DSyncBase {
		public static final String TypeName = "DCardPosition";
		
		private String ownerToken;
		private EPileType pileType;
		private int pileIndex;
		private int pileSize;

		public static class K {
			public static final String ownerToken = "ownerToken";
			public static final String pileType = "pileType";
			public static final String pileIndex = "pileIndex";
			public static final String pileSize = "pileSize";
		}

		public DCardPosition() {
			init();
		}

		@Override
		protected void init() {
			ownerToken = "";
			pileType = EPileType.NumberShop;
			pileIndex = 0;
			pileSize = 0;
		}
		
		public String getOwnerToken() {
			return ownerToken;
		}
		
		public void setOwnerToken(String ownerToken) {
			this.ownerToken = ownerToken;
		}
		
		public EPileType getPileType() {
			return pileType;
		}
		
		public void setPileType(EPileType pileType) {
			this.pileType = pileType;
		}
		
		public int getPileIndex() {
			return pileIndex;
		}
		
		public void setPileIndex(int pileIndex) {
			this.pileIndex = pileIndex;
		}
		
		public int getPileSize() {
			return pileSize;
		}
		
		public void setPileSize(int pileSize) {
			this.pileSize = pileSize;
		}
		

		public static DCardPosition parseJSONObject(JSONObject jsonObject) {
			DCardPosition _value = new DCardPosition();
			if (!jsonObject.isEmpty()) {
				_value.applyRecord(jsonObject);
			}
			return _value;
		}
		
		public static List<DCardPosition> parseJSONArray(JSONArray jsonArray) {
			ArrayList<DCardPosition> list = new ArrayList<DCardPosition>();
			for (int i = 0; i < jsonArray.size(); i++) {
				DCardPosition _value = new DCardPosition();
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
			jsonObject.put(K.ownerToken, ownerToken);
			jsonObject.put(K.pileType, pileType.ordinal());
			jsonObject.put(K.pileIndex, pileIndex);
			jsonObject.put(K.pileSize, pileSize);
		}

		@Override
		public void applyRecord(JSONObject jsonObject) {
			ownerToken = jsonObject.getString(K.ownerToken);
			pileType = EPileType.values()[(jsonObject.getIntValue(K.pileType))];
			pileIndex = jsonObject.getIntValue(K.pileIndex);
			pileSize = jsonObject.getIntValue(K.pileSize);
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof DCardPosition)) {
				return false;
			}
			DCardPosition _value = (DCardPosition) obj;
			if (!this.ownerToken.equals(_value.ownerToken)) {
				return false;
			}
			if (!this.pileType.equals(_value.pileType)) {
				return false;
			}
			if (this.pileIndex != _value.pileIndex) {
				return false;
			}
			if (this.pileSize != _value.pileSize) {
				return false;
			}
			return true;
		}
		
		public DCardPosition copy() {
			DCardPosition _value = new DCardPosition();
			_value.ownerToken = this.ownerToken;
			_value.pileType = this.pileType;
			_value.pileIndex = this.pileIndex;
			_value.pileSize = this.pileSize;
			return _value;
		}
		
		public DCardPosition deepCopy() {
			DCardPosition _value = new DCardPosition();
			_value.ownerToken = this.ownerToken;
			_value.pileType = this.pileType;
			_value.pileIndex = this.pileIndex;
			_value.pileSize = this.pileSize;
			return _value;
		}
	}
	
	public static class DHuman extends DSyncBase {
		public static final String TypeName = "DHuman";
		
		private String token;
		private int actionPoint;
		private int winPoint;
		private int diamond;
		private int handPileSize;
		private int monsterPileSize;

		public static class K {
			public static final String token = "token";
			public static final String actionPoint = "actionPoint";
			public static final String winPoint = "winPoint";
			public static final String diamond = "diamond";
			public static final String handPileSize = "handPileSize";
			public static final String monsterPileSize = "monsterPileSize";
		}

		public DHuman() {
			init();
		}

		@Override
		protected void init() {
			token = "";
			actionPoint = 0;
			winPoint = 0;
			diamond = 0;
			handPileSize = 0;
			monsterPileSize = 0;
		}
		
		public String getToken() {
			return token;
		}
		
		public void setToken(String token) {
			this.token = token;
		}
		
		public int getActionPoint() {
			return actionPoint;
		}
		
		public void setActionPoint(int actionPoint) {
			this.actionPoint = actionPoint;
		}
		
		public int getWinPoint() {
			return winPoint;
		}
		
		public void setWinPoint(int winPoint) {
			this.winPoint = winPoint;
		}
		
		public int getDiamond() {
			return diamond;
		}
		
		public void setDiamond(int diamond) {
			this.diamond = diamond;
		}
		
		public int getHandPileSize() {
			return handPileSize;
		}
		
		public void setHandPileSize(int handPileSize) {
			this.handPileSize = handPileSize;
		}
		
		public int getMonsterPileSize() {
			return monsterPileSize;
		}
		
		public void setMonsterPileSize(int monsterPileSize) {
			this.monsterPileSize = monsterPileSize;
		}
		

		public static DHuman parseJSONObject(JSONObject jsonObject) {
			DHuman _value = new DHuman();
			if (!jsonObject.isEmpty()) {
				_value.applyRecord(jsonObject);
			}
			return _value;
		}
		
		public static List<DHuman> parseJSONArray(JSONArray jsonArray) {
			ArrayList<DHuman> list = new ArrayList<DHuman>();
			for (int i = 0; i < jsonArray.size(); i++) {
				DHuman _value = new DHuman();
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
			jsonObject.put(K.token, token);
			jsonObject.put(K.actionPoint, actionPoint);
			jsonObject.put(K.winPoint, winPoint);
			jsonObject.put(K.diamond, diamond);
			jsonObject.put(K.handPileSize, handPileSize);
			jsonObject.put(K.monsterPileSize, monsterPileSize);
		}

		@Override
		public void applyRecord(JSONObject jsonObject) {
			token = jsonObject.getString(K.token);
			actionPoint = jsonObject.getIntValue(K.actionPoint);
			winPoint = jsonObject.getIntValue(K.winPoint);
			diamond = jsonObject.getIntValue(K.diamond);
			handPileSize = jsonObject.getIntValue(K.handPileSize);
			monsterPileSize = jsonObject.getIntValue(K.monsterPileSize);
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof DHuman)) {
				return false;
			}
			DHuman _value = (DHuman) obj;
			if (!this.token.equals(_value.token)) {
				return false;
			}
			if (this.actionPoint != _value.actionPoint) {
				return false;
			}
			if (this.winPoint != _value.winPoint) {
				return false;
			}
			if (this.diamond != _value.diamond) {
				return false;
			}
			if (this.handPileSize != _value.handPileSize) {
				return false;
			}
			if (this.monsterPileSize != _value.monsterPileSize) {
				return false;
			}
			return true;
		}
		
		public DHuman copy() {
			DHuman _value = new DHuman();
			_value.token = this.token;
			_value.actionPoint = this.actionPoint;
			_value.winPoint = this.winPoint;
			_value.diamond = this.diamond;
			_value.handPileSize = this.handPileSize;
			_value.monsterPileSize = this.monsterPileSize;
			return _value;
		}
		
		public DHuman deepCopy() {
			DHuman _value = new DHuman();
			_value.token = this.token;
			_value.actionPoint = this.actionPoint;
			_value.winPoint = this.winPoint;
			_value.diamond = this.diamond;
			_value.handPileSize = this.handPileSize;
			_value.monsterPileSize = this.monsterPileSize;
			return _value;
		}
	}
	

	public static enum ECardType {
		Number,
		Monster,
	}
	public static enum EPileType {
		NumberShop,
		NumberPile,
		NumberDiscardPile,
		MonsterShop,
		MonsterPile,
		MonsterDiscardPile,
		Hand,
		Gate,
		FieldMonster,
	}


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

