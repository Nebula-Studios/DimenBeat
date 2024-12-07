extends Button


# Called when the node enters the scene tree for the first time.
func _ready() -> void:
	pass

# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(_delta: float) -> void:
	pass

func _gui_input(event):
	if event is InputEventMouseButton:
		if event.button_index == MOUSE_BUTTON_LEFT and event.pressed:
			#不知为何无效，等待修复
			#var sound = AudioStreamOggVorbis.load_from_file("res://assets/audio/se/menu/Confirm_2.ogg")
			#sound.instantiate_playback().play()
			get_tree().quit()
