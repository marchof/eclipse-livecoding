package org.jacoco.tools.eclipse.lifecoding;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

/**
 * Test handler to identify the current Git Repo and print it to the console.
 */
public class PrintCurrentRepoHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		System.out.println(Utils.getCurrentRepository(event));

		return null;
	}

}
