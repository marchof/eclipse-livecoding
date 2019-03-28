package org.jacoco.tools.eclipse.lifecoding;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.egit.core.internal.util.ResourceUtil;
import org.eclipse.egit.ui.internal.selection.SelectionUtils;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
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

	public static Set<String> getTags(Repository repo) throws IOException {
		Set<String> names = new HashSet<>();
		for (Ref r : repo.getRefDatabase().getRefsByPrefix(Constants.R_TAGS)) {
			names.add(r.getName());
		}
		return names;
	}

	public static Set<String> getTagsOfCurrentHead(Repository repo) throws IOException {
		Set<String> tags = new HashSet<>();
		Ref head = repo.exactRef(Constants.HEAD);
		for (Ref r : repo.getRefDatabase().getRefsByPrefix(Constants.R_TAGS)) {
			if (isTagForCommit(r, head)) {
				tags.add(r.getName());
			}
		}
		return tags;
	}

	private static boolean isTagForCommit(Ref tag, Ref commit) {
		// lightweight tags
		ObjectId commitId = commit.getObjectId();
		if (commitId.equals(tag.getObjectId())) {
			return true;
		}
		// annotated tags
		if (commit.getObjectId().equals(tag.getPeeledObjectId())) {
			return true;
		}
		return false;
	}

}
