extends Node


# Called when the node enters the scene tree for the first time.
func _ready() -> void:
	#初始化steam并获取信息
	GlobalSteam.initialize_steam()
	
	# 检测是否初始化
	if Global.steam_id == 0:
		print("Failed to initialize Steam?")
		get_tree().quit()
	
	# 正版检测
	if !Global.is_online || !Global.is_owned:
		print("You don't seem to have purchased the game")
		get_tree().quit()


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(_delta: float) -> void:
	pass
