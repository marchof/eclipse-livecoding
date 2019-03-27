package org.jacoco.tools.eclipse.lifecoding;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.egit.core.internal.util.ResourceUtil;
import org.eclipse.egit.ui.internal.selection.SelectionUtils;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Collection of shared utility methods.
 */
public class Utils {

	/**
	 * Tries to identify a current repository from a structured selection or from
	 * the active editor.
	 * 
	 * @return current repository or <code>null</code>
	 */
	public static Repository getCurrentRepository(ExecutionEvent event) throws ExecutionException {

		Repository repo = SelectionUtils.getRepository(HandlerUtil.getCurrentStructuredSelection(event));
		if (repo != null) {
			return repo;
		}

		IEditorInput input = HandlerUtil.getActiveEditorInput(event);
		if (input != null) {
			IResource resource = input.getAdapter(IResource.class);
			if (resource != null) {
				return ResourceUtil.getRepository(resource);
			}
		}

		return null;
	}

}
