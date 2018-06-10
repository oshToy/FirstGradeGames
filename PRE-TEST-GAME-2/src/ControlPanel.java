import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class ControlPanel extends JPanel {
	private String arr []={"EASY","NORMAL","HARD"};
	public ControlPanel(){
		setFocusable(false);
	//setLayout(new GridLayout(4, 1));
		add(new RadioButton());
		add(new speedSlider());
		add(new JLabel("LEVEL :"));
		add(new combo());
	}

	class RadioButton extends JPanel {

		public RadioButton(){
			JRadioButton blackButoon=new JRadioButton("black");
			blackButoon.setFocusable(false);
			JRadioButton blueButoon=new JRadioButton("blue");
			blueButoon.setFocusable(false);
			JRadioButton greenButoon=new JRadioButton("green");
			greenButoon.setFocusable(false);
			JRadioButton pinkButoon=new JRadioButton("pink");
			pinkButoon.setFocusable(false);
			ButtonGroup colorOptions=new ButtonGroup();
			colorOptions.add(blackButoon);
			colorOptions.add(blueButoon);
			colorOptions.add(greenButoon);
			colorOptions.add(pinkButoon);
			blackButoon.setMnemonic('b');
			blueButoon.setMnemonic('u');
			greenButoon.setMnemonic('g');
			pinkButoon.setMnemonic('p');
			add(blackButoon);
			add(blueButoon);
			add(greenButoon);
			add(pinkButoon);
			blackButoon.addActionListener(new RadioListener());
			blueButoon.addActionListener(new RadioListener());
			greenButoon.addActionListener(new RadioListener());
			pinkButoon.addActionListener(new RadioListener());
			
		}
	}
	class RadioListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("blue"))
				GameFrame.plane.setColorPlane(Color.BLUE);
			else if(e.getActionCommand().equals("black"))
				GameFrame.plane.setColorPlane(Color.black);
			else if(e.getActionCommand().equals("green"))
				GameFrame.plane.setColorPlane(Color.green);
			else if(e.getActionCommand().equals("pink"))
				GameFrame.plane.setColorPlane(Color.pink);
			
			
			
		}
		
	}
    class speedSlider extends JPanel{
    	public speedSlider(){
    		JSlider speedSlider=new JSlider();
    		speedSlider.setFocusable(false);
    		speedSlider.setInverted(true);
    		speedSlider.setMaximum(50);
    		speedSlider.setToolTipText("Speed Of Plane");
    		speedSlider.setMajorTickSpacing(25);
    		speedSlider.setPaintTicks(true);
    		speedSlider.setValue(50);
    		speedSlider.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					
					GameFrame.plane.setPlaneDelay(speedSlider.getValue());
					
				}
			});
    		add(speedSlider);
    	}
    }
    class combo extends JComboBox{
    	
    	public combo(){
    		super(arr);
    		setSelectedIndex(1);
    		setFocusable(false);
    		setToolTipText("Amount Of Bricks");
    		addItemListener(new ItemListener() {
				
				@Override
				public void itemStateChanged(ItemEvent e) {
					System.out.println(e.getItem());
					if(e.getItem().equals("HARD")){
						GameFrame.brickMakerDelay=0;
					}
					if(e.getItem().equals("EASY")){
						GameFrame.brickMakerDelay=3000;
						if(e.getItem().equals("NORMAL")){
							GameFrame.brickMakerDelay=150;
						}
					}
					
				}
			});
    		
    		
    		
    		
    	}
    }
}

