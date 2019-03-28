package org.jacoco.tools.eclipse.lifecoding;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Handler which does a HARD reset to the next (lexical sorting) tag which
 * follows the tag associated with the current branch.
 */
public class MoveToNextTagHandler extends AbstractMoveTagHandler {

	@Override
	protected String getTargetTag(Set<String> allTags, Set<String> currentTags) {
		SortedSet<String> candidates = new TreeSet<>(allTags);
		for (String current : currentTags) {
			candidates = candidates.tailSet(current);
		}
		if (!candidates.isEmpty()) {
			candidates.remove(candidates.first());
		}
		return candidates.isEmpty() ? null : candidates.first();
	}

}
