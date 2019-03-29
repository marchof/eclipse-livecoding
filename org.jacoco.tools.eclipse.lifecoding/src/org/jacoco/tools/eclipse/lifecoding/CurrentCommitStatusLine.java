package org.jacoco.tools.eclipse.lifecoding;

import java.io.IOException;

import org.eclipse.jgit.events.RefsChangedListener;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.menus.WorkbenchWindowControlContribution;

/**
 * Shows the current commit in the status line.
 */
public class CurrentCommitStatusLine extends WorkbenchWindowControlContribution {

	private RefsChangedListener refListener = event -> readHead(event.getRepository());

	private Label label;

	@Override
	protected Control createControl(Composite parent) {
		Repository.getGlobalListenerList().addRefsChangedListener(refListener);
		label = new Label(parent, SWT.NONE);
		label.setText("_______________________________");
		return label;
	}

	private void readHead(Repository repo) {
		try (RevWalk rw = new RevWalk(repo)) {
			ObjectId head = repo.resolve(Constants.HEAD);
			if (head == null) {
				return;
			}
			RevCommit c = rw.parseCommit(head);
			label.getDisplay().asyncExec(() -> label.setText(c.getShortMessage()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
