extends Label

func _ready() -> void:
	set_text("Steam ID: " + String.num(Global.steam_id))
