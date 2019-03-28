package org.jacoco.tools.eclipse.lifecoding;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Handler which does a HARD reset to the previous (lexical sorting) tag which
 * follows the tag associated with the current branch.
 */
public class MoveToPreviousTagHandler extends AbstractMoveTagHandler {

	@Override
	protected String getTargetTag(Set<String> allTags, Set<String> currentTags) {
		SortedSet<String> candidates = new TreeSet<>(allTags);
		for (String current : currentTags) {
			candidates = candidates.headSet(current);
		}
		return candidates.isEmpty() ? null : candidates.last();
	}

}
