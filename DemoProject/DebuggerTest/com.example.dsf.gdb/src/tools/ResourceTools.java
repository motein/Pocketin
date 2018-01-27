package tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitorWithBlocking;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.ide.undo.CopyResourcesOperation;
import org.eclipse.ui.ide.undo.CreateFileOperation;
import org.eclipse.ui.ide.undo.DeleteResourcesOperation;
import org.eclipse.ui.ide.undo.MoveResourcesOperation;
import org.eclipse.ui.part.FileEditorInput;

public class ResourceTools {

	public static boolean isSameFile(IPath path, IResource resource) {
		if (path == null || resource == null) {
			return false;
		}
		String pathStr = path.toString();
		String resourceStr = resource.getFullPath().toString();

		return (pathStr.compareTo(resourceStr) == 0);
	}

	/**
	 * 与えられたリソースをエディタで開く
	 * 
	 * @param file
	 *            エディタでopenするファイル
	 */
	public static IEditorPart openEditorPart(final IFile file) {

		IEditorPart ret = null;
		IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		try {
			// File名からの Default Editorを入手
			IEditorRegistry editorReg = PlatformUI.getWorkbench()
					.getEditorRegistry();
			IEditorDescriptor editorDesc = editorReg.getDefaultEditor(file
					.getName()); // File名からの Default Editorを入手

			if (editorDesc != null) {
				// Open withのように Editor IDの同じものがなければ Editorを開く
				ret = page.openEditor(new FileEditorInput(file),
						editorDesc.getId(), true, IWorkbenchPage.MATCH_INPUT
								| IWorkbenchPage.MATCH_ID);

				// その Fileの Double Click時の Editorを設定
				IDE.setDefaultEditor(file, editorDesc.getId());
			} else {
				IDE.openEditor(page, file);
			}
		} catch (PartInitException e) {
		}
		return ret;
	}

	public static void openEditor(final IFile file) {

		Display display = PlatformUI.getWorkbench().getDisplay();
		display.asyncExec(new Runnable() {

			@Override
			public void run() {
				openEditorPart(file);
			}
		});
	}

	/**
	 * 与えられたリソースのエディタが存在していれば、アクティブにする
	 * 
	 * @param file
	 *            エディタでopenするファイル
	 */
	public static void activateEditor(final IFile file) {
		TdeResourceUtil.activateEditor(file);
	}

	/**
	 * 与えられたlocationがワークスペース内のプロジェクトとして管理されていればIProjectを返す
	 * 
	 * @param location
	 * @return
	 */
	public static IProject getProjectFromPath(IPath location) {
		if (location == null) {
			return null;
		}

		IWorkspaceRoot root = getWorkspaceRoot();
		if (root == null) {
			return null;
		}

		// ワークスペースで管理されているプロジェクトのリストを取得する
		IProject[] projects = root.getProjects();
		if (projects == null) {
			return null;
		}

		for (int i = 0; i < projects.length; i++) {
			IProject project = projects[i];
			if (project != null) {
				if (isLocationOfProject(project, location)) {
					return project;
				}
			}
		}

		return null;
	}

	/**
	 * 指定した名前に一致するプロジェクトがワークスペース内にあれば、それを返す
	 * 
	 * @param name
	 *            (if empty or null, returns null)
	 * @return project or null
	 */
	public static IProject getProjectFromName(String name) {
		if (StringTools.isSetAny(name)) {
			IWorkspaceRoot root = getWorkspaceRoot();
			if (root != null) {
				return root.getProject(name);
			}
		}

		return null;
	}

	/**
	 * 与えられたリソースが属するプロジェクトのリストを返す
	 * 
	 * @param resources
	 * @return
	 */
	public static List<IProject> getProjectsFromResources(
			Collection<IResource> resources) {
		List<IProject> list = new ArrayList<IProject>();

		for (IResource resource : resources) {
			if (resource != null) {
				IProject project = resource.getProject();
				if (project != null && !list.contains(project)) {
					list.add(project);
				}
			}
		}

		return list;
	}

	/**
	 * ワークスペース内で、nameで指定したプロジェクトの重複番号を返す
	 * 
	 * name[数字] 数字が0以外の場合、数字+1を返す nameのみ存在する場合は1を返す name0は無視する
	 * 
	 * e.g.) nameまたはname1 (name2, ... namen)が存在しない場合, 0を返す nameだけが存在する場合1を返す
	 * name, name1が存在する場合2を返す name, name3が存在する場合4を返す name0が存在する場合0を返す
	 * 
	 * @param name
	 * @return name[数字]が存在する場合、最大の数字+1, nameのみ存在する場合は1
	 *         name[数字]が存在しない場合(name0は無視)0を返す
	 */
	public static int getDuplicationProjectCount(String name) {

		Assert.isTrue(StringTools.isSetAny(name));

		IWorkspaceRoot root = getWorkspaceRoot();
		Assert.isNotNull(root);

		// ワークスペースで管理されているプロジェクトのリストを取得する
		IProject[] projects = root.getProjects();
		Assert.isNotNull(projects);

		boolean existSameName = false;
		int maxNumber = -1;

		for (int i = 0; i < projects.length; i++) {
			IProject project = projects[i];
			if (project != null) {
				String projectName = project.getName().trim();
				if (StringTools.isSetAny(projectName)) {
					if (projectName.matches("^" + name.trim() + "[1-9][0-9]*$")) {
						// 数字付きnameが存在する
						String[] results = projectName.split(name.trim(), -1);
						if (results.length > 1) {
							Integer num = Integer.valueOf(results[1]);
							maxNumber = Math.max(maxNumber, num);
						}
					} else if (projectName.compareTo(name.trim()) == 0) {
						existSameName = true;
					}
				}
			}
		}

		if (maxNumber > 0) {
			return maxNumber + 1;
		} else if (existSameName) {
			return 1;
		}

		return 0;
	}

	/**
	 * 指定 Path内で、nameで指定した File/Folderの重複番号を返す
	 * 
	 * name[数字] 数字が0以外の場合、数字+1を返す nameのみ存在する場合は1を返す name0は無視する
	 * 
	 * e.g.) nameまたはname1 (name2, ... namen)が存在しない場合, 0を返す nameだけが存在する場合1を返す
	 * name, name1が存在する場合2を返す name, name3が存在する場合4を返す name0が存在する場合0を返す
	 * 
	 * @param name
	 * @return name[数字]が存在する場合、最大の数字+1, nameのみ存在する場合は1
	 *         name[数字]が存在しない場合(name0は無視)0を返す
	 */
	public static int getDuplicationResourceCount(IContainer parent,
			String name, String suffix) {

		Assert.isTrue(StringTools.isSetAny(name));
		if (parent == null)
			parent = getWorkspaceRoot();
		Assert.isNotNull(parent);
		if (suffix == null)
			suffix = "";

		// 子供のリストを取得する
		IResource members[];
		try {
			members = parent.members();
			Assert.isNotNull(members);

			boolean existSameName = false;
			int maxNumber = -1;

			for (int i = 0; i < members.length; i++) {
				IResource res = members[i];
				if (res == null)
					continue;
				String resName = res.getName().trim();
				if (!StringTools.isSetAny(resName))
					continue;
				if (!resName.endsWith(suffix))
					continue;
				resName = resName.substring(0, resName.lastIndexOf('.'));
				if (resName.matches("^" + name.trim() + "[1-9][0-9]*$")) {
					// 数字付きnameが存在する
					String[] results = resName.split(name.trim(), -1);
					if (results.length > 1) {
						Integer num = Integer.valueOf(results[1]);
						maxNumber = Math.max(maxNumber, num);
					}
				} else if (resName.compareTo(name.trim()) == 0) {
					existSameName = true;
				}
			}
			if (maxNumber > 0) {
				return maxNumber + 1;
			} else if (existSameName) {
				return 1;
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * WorkspaceRootを返す
	 * 
	 * @return
	 */
	public static IWorkspaceRoot getWorkspaceRoot() {

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		if (workspace != null) {
			IWorkspaceRoot root = workspace.getRoot();
			return root;
		}

		return null;

	}

	/**
	 * 与えられたファイルを、ワークスペースに作成する
	 * 
	 * @param filePath
	 * @return
	 */
	public static IFile getFileHandle(IPath filePath) {
		IWorkspaceRoot root = getWorkspaceRoot();
		if (root != null) {
			return root.getFile(filePath);
		}

		return null;
	}

	/**
	 * Activeなページのセレクションを取得する
	 * 
	 * @return セレクション情報
	 */
	public static ISelection getWorkbenchSelection() {

		IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench == null) {
			return null;
		}

		IWorkbenchWindow activeWindow = workbench.getActiveWorkbenchWindow();
		if (activeWindow == null) {
			return null;
		}

		IWorkbenchPage page = activeWindow.getActivePage();
		if (page == null) {
			return null;
		}

		return page.getSelection();
	}

	/**
	 * Returns IEditorPart that has specified resource as its input
	 * 
	 * @param resource
	 * @return
	 */
	public static IEditorPart getEditorPart(IResource resource) {
		IEditorReference[] editors = TdeResourceUtil2.getEditorReferences();
		if (editors != null) {
			for (IEditorReference editor : editors) {
				if (editor != null) {
					try {
						IEditorInput input = editor.getEditorInput();
						if (input instanceof FileEditorInput) {
							FileEditorInput finput = (FileEditorInput) input;
							if (resource.equals(finput.getFile())) {
								IEditorPart editorPart = editor.getEditor(true);
								return editorPart;
							}
						}

					} catch (PartInitException e) {
						e.printStackTrace();
					}
				}
			}
		}

		return null;
	}

	/**
	 * 与えられたEditorInputからIFileを取得して返す
	 * 
	 * @param input
	 * @return IFileまたはnull
	 */
	public static IFile getFileFromEditorInput(IEditorInput input) {
		if (input instanceof IFileEditorInput) {
			IFileEditorInput fileinput = (IFileEditorInput) input;
			return fileinput.getFile();
		}

		return null;
	}

	/***
	 * 指定した名前のプロジェクトがワークスペースに存在するかどうかを調べる
	 * 
	 * @param name
	 *            プロジェクト名
	 * @return 指定した名前のプロジェクトがワークスペースに存在する場合trueを返す
	 */
	public static boolean existProject(String name) {

		Assert.isTrue(StringTools.isSetAny(name));
		String nameToFind = name.trim();

		IWorkspaceRoot root = getWorkspaceRoot();
		Assert.isNotNull(root);

		// ワークスペースで管理されているプロジェクトのリストを取得する
		IProject[] projects = root.getProjects();
		Assert.isNotNull(projects);

		for (int i = 0; i < projects.length; i++) {
			IProject project = projects[i];
			if (project != null) {
				String projectName = project.getName().trim();
				// 指定された名前のプロジェクトが存在する
				if (nameToFind.compareTo(projectName) == 0) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * 指定した名前のリソースがプロジェクトに存在すればtrueを返す
	 * 
	 * @param project
	 *            プロジェクト名
	 * @param name
	 *            リソース名
	 * @return
	 */
	public static boolean contains(IProject project, String name) {
		Assert.isNotNull(project);
		if (StringTools.isSetAny(name)) {
			IPath path = new Path("/" + name);
			IResource resource = project.findMember(path);
			if (resource != null) {
				return true;
			}
		}

		return false;
	}

//	/**
//	 * プロジェクトの名前を変更する
//	 * 
//	 * @param project
//	 *            名前を変更するプロジェクト
//	 * @param name
//	 *            変更後の名前
//	 * @param monitor
//	 *            nullでも可
//	 */
//	public static void renameProject(IProject project, String name,
//			IProgressMonitorWithBlocking monitor) {
//
//		Assert.isNotNull(project);
//		Assert.isTrue(StringTools.isSetAny(name));
//		if (project.getName().compareTo(name) != 0) {
//			// 名前が変更される場合のみ処理をおこなう
//			IPath newPath = new Path("/" + name);
//			IPath location = project.getLocation();
//
//			MoveResourcesOperation moveOp = new MoveResourcesOperation(project,
//					newPath, "ResoruceTools#Rename");
//			try {
//				moveOp.execute(monitor, null);
//
//				// Workspace外の Projectの場合 Locationの Renameを明示的に実施しなければならない
//				IPath root = ResourcesPlugin.getWorkspace().getRoot()
//						.getLocation();
//				if (!location.removeLastSegments(1).equals(root)) {
//					// Move location
//					ResourceChange rc = new TplProjRenameLocationChange(name,
//							name, name);
//					rc.perform(new NullProgressMonitor());
//				}
//			} catch (ExecutionException e) {
//				e.printStackTrace();
//			} catch (CoreException e) {
//				e.printStackTrace();
//			}
//		}
//	}

	/**
	 * リソースをリネームする
	 * 
	 * @param resource
	 *            リネーム対象となるリソース
	 * @param name
	 *            新しいリソース
	 */
	public static void renameResource(IResource resource, IPath newPath) {
		Assert.isNotNull(resource);
		Assert.isNotNull(newPath);

		MoveResourcesOperation moveOp = new MoveResourcesOperation(resource,
				newPath, "ResourceTools#renameResource");

		try {
			moveOp.execute(null, null);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	/**
	 * リソースのコピーを作成する
	 * 
	 * 同一プロジェクト内でコピーを作成する
	 * 
	 * @param resource
	 *            　コピーを作成するリソース
	 * @param name
	 *            作成するコピーの名前
	 */
	public static void copyResource(IResource resource, String name) {

		Assert.isNotNull(resource);
		Assert.isTrue(StringTools.isSetAny(name));

		IPath newPath = new Path("/" + name);

		CopyResourcesOperation copyOp = new CopyResourcesOperation(resource,
				newPath, "ResoruceTools#Copy");
		try {
			copyOp.execute(null, null);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 指定したプロジェクト内に指定した名前でリソースのコピーを作成する
	 * 
	 * @param resource
	 *            コピーされるリソース
	 * @param projectToCopyIn
	 *            コピー先のプロジェクト
	 * @param name
	 *            新しい名前
	 */
	public static void copyResourceToProject(IResource resource,
			IProject projectToCopyIn, String name) {

		Assert.isNotNull(resource);
		Assert.isNotNull(projectToCopyIn);
		Assert.isTrue(StringTools.isSetAny(name));

		IUndoableOperation op = getCopyResourceToProjectOperation(resource,
				projectToCopyIn, name);
		try {
			op.execute(null, null);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 指定したプロジェクト内に指定した名前でリソースのコピーを作成するためのundoable operationを返す
	 * 
	 * @param resource
	 *            コピー元のリソース
	 * @param projectToCopyIn
	 *            コピー先のプロジェクト
	 * @param name
	 *            新しい名前
	 * @return
	 */
	public static IUndoableOperation getCopyResourceToProjectOperation(
			IResource resource, IProject projectToCopyIn, String name) {

		Assert.isNotNull(resource);
		Assert.isNotNull(projectToCopyIn);
		Assert.isTrue(StringTools.isSetAny(name));
		IPath newPath = new Path("/" + projectToCopyIn.getName() + "/" + name);

		String label = String.format("Copy (%s)", name);
		CopyResourcesOperation op = new CopyResourcesOperation(resource,
				newPath, label);

		return op;
	}

	/**
	 * 指定されたファイルをプロジェクトにコピーする
	 * 
	 * @param sourceFile
	 *            コピー元のファイル
	 * @param projectToCopyIn
	 *            コピー先のプロジェクト
	 * @param name
	 *            コピー先のファイル名
	 * @throws FileNotFoundException
	 * @throws CoreException
	 */
	public static void copyFileToProject(File sourceFile,
			IProject projectToCopyIn, String name)
			throws FileNotFoundException, CoreException {

		Assert.isNotNull(sourceFile);
		Assert.isNotNull(projectToCopyIn);
		Assert.isTrue(StringTools.isSetAny(name));

		IPath target = projectToCopyIn.getFullPath().append(name);
		IFile handle = ResourceTools.getFileHandle(target);

		InputStream contents = new FileInputStream(sourceFile);
		try {
			if (handle != null) {
				if (handle.exists()) {
					// インポート先が存在する
					handle.setContents(contents, true, true, null);
				} else {
					// 新規作成する
					String label = String.format("Copy (%s)", name);
					CreateFileOperation op = new CreateFileOperation(handle,
							null, contents, label);
					try {
						op.execute(null, null);
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
				}
			}
		} finally {
			try {
				contents.close();
			} catch (IOException e) {
				// do nothing
			}
		}
	}

	/**
	 * リソースをファイルに書き込む
	 * 
	 * @param resource
	 * @param file
	 *            すでに存在してル場合、overwriteする
	 */
	public static void copyResourceToDirectory(IResource resource, File file) {
		Assert.isNotNull(resource);
		Assert.isNotNull(file);

		IPath srcpath = resource.getLocation();
		try {
			FileReader reader = new FileReader(srcpath.toFile());
			BufferedReader breader = new BufferedReader(reader);
			FileWriter writer = new FileWriter(file);
			BufferedWriter bwriter = new BufferedWriter(writer);
			String line;
			do {
				line = breader.readLine();
				if (line != null) {
					bwriter.write(line);
					bwriter.newLine();
				}
			} while (line != null);

			bwriter.flush();
			bwriter.close();
			writer.close();
			breader.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 与えられたリソースを削除する
	 * 
	 * @param resources
	 *            削除するリソースのリスト
	 */
	public static void deleteResources(IResource[] resources) {

		Assert.isNotNull(resources);

		DeleteResourcesOperation deleteOp = new DeleteResourcesOperation(
				resources, "ResoruceTools#Delete", true);
		try {
			deleteOp.execute(null, null);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 与えられたリソースを削除する
	 * 
	 * @param resource
	 *            削除するリソース
	 */
	public static void deleteResource(IResource resource) {

		Assert.isNotNull(resource);
		IResource[] resources = new IResource[] { resource };
		deleteResources(resources);
	}

	/**
	 * 与えられたselectionからプロジェクトとメンバを取得する
	 * 
	 * @param selection
	 *            プロジェクトとメンバを取得する元
	 * @param projects
	 *            取得されたプロジェクトを入れるSet (nullを渡すとexceptionを投げる)
	 * @param members
	 *            取得されたメンバを入れるSet (nullを渡すとexceptionを投げる)
	 */
	public static void getSelectedProjectsAndMembers(ISelection selection,
			Set<IProject> projects, Set<IResource> members) {

		Assert.isNotNull(projects);
		Assert.isNotNull(members);

		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection) selection;
			Object[] items = ss.toArray();
			for (int i = 0; i < items.length; i++) {
				Object item = items[i];
				if (item instanceof IResource) {
					IResource resource = (IResource) item;
					if (resource instanceof IProject) {
						// プロジェクトである
						projects.add((IProject) resource);

					} else {
						// メンバーである
						members.add(resource);
					}
				}
			}
		}
	}

	/**
	 * 指定したパスがプロジェクトの実体であればtrueを返す
	 * 
	 * @param project
	 *            プロジェクト
	 * @param location
	 *            プロジェクトのlocationかどうかを調べるパス
	 * 
	 * @return
	 */
	private static boolean isLocationOfProject(IProject project, IPath location) {
		if (project == null || location == null) {
			return false;
		}

		IPath projectLocation = project.getLocation();
		if (projectLocation == null) {
			return false;
		}

		// それぞれのデバイスを取得する
		String deviceOfProject = projectLocation.getDevice();
		String deviceOfLocation = location.getDevice();

		if (StringTools.isSetAny(deviceOfProject)
				&& !StringTools.isSetAny(deviceOfLocation)) {
			// rootPathにdeviceがセットされてるのに、dirPathにdeviceがセットされていない場合
			// dirPathにもデバイスをセットする
			location = location.setDevice(deviceOfProject);
		}

		return projectLocation.isPrefixOf(location);
	}
}
