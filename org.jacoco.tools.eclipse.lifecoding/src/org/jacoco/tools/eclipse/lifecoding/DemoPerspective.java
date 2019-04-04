package org.jacoco.tools.eclipse.lifecoding;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class DemoPerspective implements IPerspectiveFactory {

	@Override
	public void createInitialLayout(IPageLayout layout) {

		IFolderLayout bottom = layout.createFolder("bottom", IPageLayout.BOTTOM, 0.75f, IPageLayout.ID_EDITOR_AREA);
		bottom.addView("org.eclipse.jdt.junit.ResultView");
		bottom.addView("org.eclipse.ui.console.ConsoleView");
		bottom.addPlaceholder("*");

		IFolderLayout left = layout.createFolder("left", IPageLayout.LEFT, 0.25f, IPageLayout.ID_EDITOR_AREA);
		left.addView("org.eclipse.jdt.ui.PackageExplorer");
		left.addView("org.eclipse.ui.views.ResourceNavigator");

	}

}
