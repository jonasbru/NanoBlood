/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nanoblood.jbox2dtests;

import javax.swing.JFrame;
import org.jbox2d.testbed.framework.TestbedFrame;
import org.jbox2d.testbed.framework.TestbedModel;
import org.jbox2d.testbed.framework.TestbedPanel;
import org.jbox2d.testbed.framework.TestbedSetting;
import org.jbox2d.testbed.framework.TestbedSetting.SettingType;
import org.jbox2d.testbed.framework.j2d.TestPanelJ2D;

/**
 *
 * @author troll
 */
public class JBox2dTests {

	private static double width = 400;
public static final String GRAVITY_SETTING = "Gravity"; // put our setting key somewhere
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {

		TestbedModel model = new TestbedModel();         // create our model

		// add tests
		model.addCategory("My Tests");
		VerticalStack vs = new VerticalStack();
		model.addTest(vs);

        // add our custom setting "My Range Setting", with a default value of 10, between 0 and 20
		model.getSettings().addSetting(new TestbedSetting(GRAVITY_SETTING, SettingType.ENGINE, false));

		TestbedPanel panel = new TestPanelJ2D(model);    // create our testbed panel

		JFrame testbed = new TestbedFrame(model, panel); // put both into our testbed frame
// etc
		testbed.setVisible(true);
		testbed.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
}
