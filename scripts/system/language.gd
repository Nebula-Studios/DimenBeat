extends Node

var button_main_start: String;
var button_main_load: String;
var button_main_dev: String;
var button_main_settings: String;
var button_main_quit: String;


func _ready() -> void:
	if(Global.language == "schinese"):
		initLanguage_zhcn()
	else: if(Global.language == "tchinese"):
		initLanguage_zh()
	else:
		initLanguage_enus()
		
func initLanguage_zhcn() -> void:
	button_main_start = "开始游戏"
	button_main_load = "读取存档"
	button_main_dev = "开发团队"
	button_main_settings = "设置"
	button_main_quit = "退出"

func initLanguage_zh() -> void:
	button_main_start = "開始遊戲"
	button_main_load = "讀取存檔"
	button_main_dev = "開發團隊"
	button_main_settings = "設定"
	button_main_quit = "退出"

func initLanguage_enus() -> void:
	button_main_start = "Start Game"
	button_main_load = "Load Game"
	button_main_dev = "Dev Team"
	button_main_settings = "Setting"
	button_main_quit = "Quit"
