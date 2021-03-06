package virtualPlanner.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import virtualPlanner.reference.Days;
import virtualPlanner.util.Block;

/**
 * This class creates a pop-up window which holds a model of the MX block-schedule
 * This window is used during the adding of courses to allow the user to input special circumstances for class meeting times (ie. Drop blocks, L blocks, Middle-of-day double blocks)
 * @author KevinGao
 *
 */
public class GUIBlockPicker {

	/**Size of the window*/
	private static final Dimension BLOCK_PICKER_SIZE = new Dimension(500, 400);

	/**The JFrame of this GUIBlockPicker instance*/
	private JFrame frame;

	/**ArrayList of the GUICheckBoxes*/
	private ArrayList<GUICheckBox[]> checkBoxes = new ArrayList<GUICheckBox[]>();

	/**
	 * Constructor for the GUIBlockPicker which creates a BlockPicker Window
	 * @param name the title of the JFrame
	 */
	protected GUIBlockPicker(String name) {

		//Frame Settings
		frame = new JFrame(name);
		frame.setSize(BLOCK_PICKER_SIZE);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setResizable(false);
		//Override close operation
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				frame.dispose();
			}
		});
		frame.setResizable(false);

		//Instantiate all the CheckBoxes
		addCheckBoxes();

		//Retrieve and show the JPanel which holds the model calendar
		frame.add(getModelCalendar());

		frame.setVisible(false);
		frame.setResizable(false);
	}

	/**
	 * @return the blocks that correspond to the currently selected GUICheckBoxes
	 */
	protected ArrayList<Block> getSelectedBlocks() {
		//Resulting ArrayList to return
		ArrayList<Block> result = new ArrayList<Block>();

		//For each checkBox
		for(GUICheckBox[] row : checkBoxes)
			for(GUICheckBox c : row)
				//If it is selected, add the block to the result
				if(c != null && c.isSelected())
					result.add(c.getBlock());

		return result;
	}

	/**
	 * Sets the visibility of the frame of this GUIBlockPicker
	 * @param visible the new visibility of the screen
	 */
	protected void setVisible(boolean visible) {
		frame.setVisible(visible);
	}

	/**
	 * Clears all of the check boxes in this GUIBlockPicker
	 */
	public void clearCheckBoxes() {
		//For each checkBox
		for(GUICheckBox[] row : checkBoxes)
			for(GUICheckBox c : row)
				if(c != null)
					c.setSelected(false);
	}

	/**
	 * This method instantiates the GUICheckBoxes, and corresponds them to their appropriate Blocks and Days
	 */
	private void addCheckBoxes() {
		// Loop through days in regular schedule.
		for(int i = 0; i < Days.values().length; i++) {
			Days day = Days.values()[i];
			for(int j = 0; j < day.getBlockCount(); j++) {
				if(checkBoxes.size() <= j)
					checkBoxes.add(new GUICheckBox[6]);
				Block block = day.getBlock(j);
				checkBoxes.get(j)[i] = new GUICheckBox(block.getBlock().getAbbreviation(), block, block.getBlock().isClass());
			}
		}
	}

	/**
	 * Adds the GUICheckBoxes to their correct locations within the JPanel
	 * @return the JPanel with all of the GUICheckBoxes modeling the Block Schedule
	 */
	private JPanel getModelCalendar() {

		//JPanel to return
		JPanel result = new JPanel();
		result.setLayout(new GridLayout(checkBoxes.size() + 1, 6));

		//First Row: Day of Week Labels
		result.add(new JLabel("Mon", JLabel.CENTER));
		result.add(new JLabel("Tue", JLabel.CENTER));
		result.add(new JLabel("Wed", JLabel.CENTER));
		result.add(new JLabel("Thu", JLabel.CENTER));
		result.add(new JLabel("Fri", JLabel.CENTER));
		result.add(new JLabel("Sat", JLabel.CENTER));

		for(GUICheckBox[] row : checkBoxes)
			for(GUICheckBox c : row)
				result.add(c == null ? new JPanel() : c);

		return result;
	}

	/**
	 * This class is a very simple extension of javax.swing.JCheckBox which helps correspond JCheckBoxes to their corresponding Block
	 * Note: This class is used instead of a Map because JCheckBox is not comparable
	 * Used only within GUIBlockPicker
	 * @author KevinGao
	 *
	 */
	@SuppressWarnings("serial")
	private class GUICheckBox extends JCheckBox {

		/**The block that corresponds to this JCheckBox*/
		private Block block;

		/**
		 * Default constructor for GUICheckBox
		 * GUICheckBox for the clickable block options
		 * @param name the name of the CheckBox
		 * @param block the corresponding block
		 * @param selectable whether to enable or disable the check box
		 */
		private GUICheckBox(String name, Block block, boolean selectable) {
			super(name);
			this.block = block;
			setEnabled(selectable);
			setFocusPainted(false);
		}

		/**
		 * @return the block that corresponds to this GUICheckBox
		 */
		private Block getBlock() {
			return block;
		}

	}

}
