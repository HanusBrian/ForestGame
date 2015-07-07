package game;

public class DialogueControl {
	
	Dialogue d;
	
	public DialogueControl(Dialogue d)
	{
		this.d = d;
		listenForKeyPressed(d);
	}
	
	private void listenForKeyPressed(Dialogue d)
	{
		d.setOnKeyPressed(e -> 
		{
			switch(e.getCode())
			{
			
				case LEFT:
				case A:
				{
					d.answerPointer.setTranslateX(0);
				} break;
			
				case RIGHT:
				case D: 
				{
					d.answerPointer.setTranslateX(308);
				} break;
				
				case ENTER:
				{
					
				} break;
				
				case ESCAPE:
				{
					Game.gsm.setState(GameStateManager.PLAY);
				} break;
				
				default: break;
			
			}
		});
	}
	
}
