package org.jacoco.tools.eclipse.lifecoding;

import java.io.IOException;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.egit.core.op.ResetOperation;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.lib.Repository;

/**
 * Handler to switch between tags.
 */
public abstract class AbstractMoveTagHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		Repository repo = Utils.getCurrentRepository(event);

		if (repo != null) {
			try {
				Set<String> allTags = Utils.getTags(repo);
				Set<String> currentTags = Utils.getTagsOfCurrentHead(repo);
				String targetTag = getTargetTag(allTags, currentTags);

				if (targetTag != null) {
					new ResetOperation(repo, targetTag, ResetType.HARD).execute(new NullProgressMonitor());
				}
			} catch (IOException | CoreException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	protected abstract String getTargetTag(Set<String> allTags, Set<String> currentTags);

}
