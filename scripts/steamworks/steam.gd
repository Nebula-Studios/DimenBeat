extends Node

# Called when the node enters the scene tree for the first time.
func _ready() -> void:
	pass

# 初始化Steam方法
func initialize_steam() -> void:
	
	var initialize_response: Dictionary = Steam.steamInitEx(true, 2919180)
	
	# 检查steam是否初始化
	if initialize_response['status'] > 0:
		print("Failed to initialize Steam！")
		get_tree().quit()
		
	# 获取需要的一些信息
	Global.is_online = Steam.loggedOn()
	Global.is_owned = Steam.isSubscribed()
	Global.steam_id = Steam.getSteamID()
	Global.language = Steam.getCurrentGameLanguage()

# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(_delta: float) -> void:
	pass
