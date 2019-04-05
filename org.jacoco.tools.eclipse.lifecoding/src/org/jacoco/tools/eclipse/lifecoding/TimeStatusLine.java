package org.jacoco.tools.eclipse.lifecoding;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.menus.WorkbenchWindowControlContribution;

/**
 * Shows the elapsed minutes.
 */
public class TimeStatusLine extends WorkbenchWindowControlContribution {

	private Label label;
	private Instant start;

	@Override
	protected Control createControl(Composite parent) {
		label = new Label(parent, SWT.NONE);
		reset();
		Executors.newScheduledThreadPool(1).scheduleAtFixedRate(this::asyncUpdate, 0, 1, TimeUnit.SECONDS);
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				reset();
			}
		});
		return label;
	}

	private void reset() {
		start = Instant.now();
		update();
	}

	private void asyncUpdate() {
		label.getDisplay().asyncExec(this::update);
	}

	public void update() {
		if (!label.isDisposed()) {
			Duration elapsed = Duration.between(start, Instant.now());
			label.setText(String.format("%02d", elapsed.toMinutes()));
		}
	}

}
