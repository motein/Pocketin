package tools;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.IFileSystem;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.internal.ui.Pair;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.ide.undo.CreateFileOperation;
import org.eclipse.ui.ide.undo.WorkspaceUndoUtil;
import org.eclipse.ui.part.FileEditorInput;


public class TdeResourceUtil {

	/**
	 * Testplanファイル用ヘッダーファイル名
	 */
	public static final String HEADER_NAME = "SYSTEM.hdr";

	/**
	 * Algorithm Libraryファイル用ヘッダーファイル名
	 */
	public static final String ALGORITHM_HEADER_NAME = "SYSTEM.ahdr";

	/**
	 * Jobスペックファイル名
	 */
	public static final String JOBSPEC_NAME = "SYSTEM.job";

	/**
	 * プロジェクト内に作成するテストプランファイル名
	 */
	public static final String TESTPLAN_NAME = ".testplan.tplx";

	/**
	 * プロジェクト内に作成するアルゴリズムライブラリファイル名
	 */
	private static final String ALGLIB_EXTENSION = "lib";

	private static final String SOURCE_FOLDER_NAME = "src";

	private static final String BINARY_FOLDER_NAME = "Debug";

	private static final String USER_FOLDER_NAME = "usr";

	private static final String SYSTEM_FOLDER_NAME = "sys";

	private static final String SOURCE_FOLDER_PATH = SOURCE_FOLDER_NAME;

	private static final String BINARY_FOLDER_PATH = BINARY_FOLDER_NAME;

	private static final String USER_SOURCE_FOLDER_PATH = SOURCE_FOLDER_NAME
			+ "/" + USER_FOLDER_NAME;

	private static final String SYSTEM_SOURCE_FOLDER_PATH = SOURCE_FOLDER_NAME
			+ "/" + SYSTEM_FOLDER_NAME;

//	/**
//	 * パラメータで与えられたプロジェクトにおいて、スペックファイルからTestplanファイルを作成する。
//	 * 
//	 * @param project
//	 *            プロジェクト
//	 * @param shell
//	 *            Shellまたはnull
//	 */
//	public static void writeTestPlans(final IProject project, final Shell shell) {
//		final List<File> filelist = new ArrayList<File>();
//		List<File> tmplist = new ArrayList<File>();
//		try {
//			TdeManager.getInstance().getSpecsAndHeader(project, filelist,
//					tmplist);
//
//			Display display = getDisplay();
//			if (display != null) {
//				display.syncExec(new Runnable() {
//					@Override
//					public void run() {
//						writeTestPlanFile(project, filelist, null, shell);
//					}
//				});
//			}
//		} catch (CoreException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			for (File file : tmplist) {
//				deleteFile(file);
//			}
//		}
//	}

//	/**
//	 * Algorithm Header(.ahdr)ファイルが存在しなければ作成する
//	 * 
//	 * @param project
//	 *            ファイルを作成するプロジェクト
//	 * @param shell
//	 *            　nullでも良い
//	 */
//	public static void assertAlgHeaderFile(IProject project, Shell shell) {
//
//		Path headerpath = new Path(ALGORITHM_HEADER_NAME);
//
//		if (!project.exists(headerpath)) {
//			// Algorithm Headerファイルが存在しないので作成する
//			AlgorithmHeader spec = new AlgorithmHeader();
//			File tmpfile = null;
//			try {
//				tmpfile = File.createTempFile("tdenew", null);
//				setupSpecification(project.getName(), spec);
//
//				// テンポラリファイルに書き出す
//				AlgorithmHeaderWriter writer = new AlgorithmHeaderWriter(
//						tmpfile.getPath());
//				writer.write(spec);
//
//				// テンポラリファイルの内容をヘッダーファイルにセットする
//				FileInputStream contents = new FileInputStream(tmpfile);
//				IFile headerfile = project.getFile(headerpath);
//				writeToResource(contents, headerfile, null, shell);
//
//			} catch (IOException e) {
//				e.printStackTrace();
//			} finally {
//				// テンポラリファイルを削除する
//				if (tmpfile != null) {
//					deleteFile(tmpfile);
//				}
//			}
//		}
//	}

//	/**
//	 * パラメータで与えられたプロジェクトにおいて、アルゴリズムスペックファイルから Algorithm Libraryファイルを作成する
//	 * 
//	 * @param project
//	 *            プロジェクト
//	 * @param shell
//	 *            (nullでも可)
//	 */
//	public static void writeAlgLibfile(final IProject project, final Shell shell) {
//		try {
//			List<File> specs = TdeManager.getInstance().getAlgSpecsAndHeader(
//					project);
//			if (specs != null) {
//				writeAlgLibfile(project, specs, null, shell);
//			} else {
//				String projectName = project.getName();
//				IFile alglibfile = project.getFile(BINARY_FOLDER_PATH + "/"
//						+ projectName + "." + ALGLIB_EXTENSION);
//				alglibfile.delete(true, null);
//			}
//		} catch (CoreException e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * 与えられた文字列をリソースに書き込む
	 * 
	 * @param contents
	 *            書き込む内容
	 * @param outResource
	 *            書き込み対象となるリソース
	 * @param monitor
	 *            Progress Monitor (nullでも可)
	 * @param shell
	 * @throws IOException
	 */
	public static void writeStringToResource(String contents,
			IFile outResource, IProgressMonitor monitor, Shell shell)
			throws IOException {
		try {
			InputStream istream = new ByteArrayInputStream(
					contents.getBytes("UTF-8"));
			writeToResource(istream, outResource, monitor, shell);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * リソースに書き込む
	 * 
	 * @param contents
	 *            書き込む内容
	 * @param outResource
	 *            書き込み対象となるリソース
	 * @param monitor
	 * @param shell
	 *            nullでも良い
	 * @throws IOException
	 */
	public static void writeToResource(InputStream contents, IFile outResource,
			IProgressMonitor monitor, Shell shell) throws IOException {
		if (outResource.exists()) {
			try {
				outResource.setContents(contents, true, true, monitor);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		} else {
			CreateFileOperation op = new CreateFileOperation(outResource, null,
					contents, "writeToFile");
			try {
				IAdaptable uiinfo;
				if (shell != null) {
					uiinfo = WorkspaceUndoUtil.getUIInfoAdapter(shell);
				} else {
					uiinfo = null;
				}
				op.execute(monitor, uiinfo);
			} catch (final ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

//	/**
//	 * 与えられたEditorInputが所属するプロジェクトのテストプランファイルを返す
//	 * 
//	 * @param input
//	 * @return テストプランファイル
//	 */
//	public static IFile getTestplan(IEditorInput input) {
//		IProject project = getProjectFromEditorInput(input);
//		if (project != null) {
//			updateTestplansWithEditorInputs(project, true);
//			return getFileFromEditorInput(input, TESTPLAN_NAME);
//		}
//
//		return null;
//	}

//	/**
//	 * 与えられたprojectに存在するテストプランファイルを返す
//	 * 
//	 * <br>
//	 * 編集中でsaveを行っていないリソースも、編集中の内容がテストプランに反映される
//	 * 
//	 * @param project
//	 *            プロジェクト
//	 * @param checkEditors
//	 *            trueの場合、編集中の内容もチェックする, falseの場合consistency
//	 *            checkから呼ばれ、編集中の内容はチェックされない
//	 * @return Test Planファイル
//	 */
//	public static IFile getTestplan(IProject project, boolean checkEditors) {
//
//		updateTestplansWithEditorInputs(project, checkEditors);
//		IResource member = project.findMember(TESTPLAN_NAME);
//
//		return (IFile) member;
//	}

//	/**
//	 * ワークスペースに存在するTsetPlanFileプロジェクトのリストを返す
//	 * 
//	 * @return
//	 */
//	public static List<IProject> getTestplanFileProjects() {
//		List<IProject> projectList = new ArrayList<IProject>();
//		IWorkspaceRoot root = ResourceTools.getWorkspaceRoot();
//
//		if (root != null) {
//			IProject[] projects = root.getProjects();
//			for (int i = 0; i < projects.length; i++) {
//				IProject project = projects[i];
//				if (project != null
//						&& NatureTools.isTestplanFileProject(project)) {
//					// Testplan Fileプロジェクトである
//					if (!projectList.contains(project)) {
//						// リストになければ追加する
//						projectList.add(project);
//					}
//				}
//			}
//		}
//
//		return projectList;
//	}

//	/**
//	 * ワークスペースに存在するTcoFileプロジェクトのリストを返す
//	 * 
//	 * @return
//	 */
//	public static List<IProject> getTcoFileProjects() {
//		List<IProject> projectList = new ArrayList<IProject>();
//		IWorkspaceRoot root = ResourceTools.getWorkspaceRoot();
//
//		if (root != null) {
//			IProject[] projects = root.getProjects();
//			for (int i = 0; i < projects.length; i++) {
//				IProject project = projects[i];
//				if (project != null && NatureTools.isTcoFileProject(project)) {
//					// Testplan Fileプロジェクトである
//					if (!projectList.contains(project)) {
//						// リストになければ追加する
//						projectList.add(project);
//					}
//				}
//			}
//		}
//
//		return projectList;
//	}

//	/**
//	 * ワークスペースに存在するLimitFileプロジェクトのリストを返す
//	 * 
//	 * @return
//	 */
//	public static List<IProject> getLimitFileProjects() {
//		List<IProject> projectList = new ArrayList<IProject>();
//		IWorkspaceRoot root = ResourceTools.getWorkspaceRoot();
//
//		if (root != null) {
//			IProject[] projects = root.getProjects();
//			for (int i = 0; i < projects.length; i++) {
//				IProject project = projects[i];
//				if (project != null && NatureTools.isLimitFileProject(project)) {
//					// Testplan Fileプロジェクトである
//					if (!projectList.contains(project)) {
//						// リストになければ追加する
//						projectList.add(project);
//					}
//				}
//			}
//		}
//
//		return projectList;
//	}

	/**
	 * Makefileを置くsrcフォルダを返す
	 * 
	 * @param project
	 * @return
	 */
	public static IContainer getSourceFolder(IProject project) {
		Assert.isNotNull(project);
		assertSourceFolders(project);

		return project.getFolder(SOURCE_FOLDER_PATH);
	}

	/**
	 * オブジェクトを格納するフォルダを返す
	 * 
	 * @param project
	 * @return
	 */
	public static IContainer getBinaryFolder(IProject project) {
		Assert.isNotNull(project);
		assertBinaryFolders(project);

		return project.getFolder(BINARY_FOLDER_PATH);
	}

	/**
	 * .cppファイルを格納するためのフォルダを返す
	 * 
	 * @param project
	 * @return
	 */
	public static IContainer getUserSourceFolder(IProject project) {
		Assert.isNotNull(project);
		assertSourceFolders(project);

		return project.getFolder(USER_SOURCE_FOLDER_PATH);
	}

	/**
	 * .exe.cppファイルを格納するためのフォルダを返す
	 * 
	 * @param project
	 * @return
	 */
	public static IContainer getSystemSourceFolder(IProject project) {
		Assert.isNotNull(project);
		assertSourceFolders(project);

		return project.getFolder(SYSTEM_SOURCE_FOLDER_PATH);
	}

	/**
	 * 与えられたprojectに存在する__<プロジェクト名>.soファイルを返す
	 * 
	 * @param project
	 * @return
	 */
	public static IResource getSystemShlibFile(IProject project) {
		IContainer folder = getBinaryFolder(project);
		IResource shlib = folder.findMember("__" + project.getName() + ".so");

		return shlib;
	}

	/**
	 * 与えられたprojectに存在する<プロジェクト名>.soファイルを返す
	 * 
	 * @param project
	 * @return
	 */
	public static IResource getShlibFile(IProject project) {
		IContainer folder = getBinaryFolder(project);
		IResource shlib = folder.findMember(project.getName() + ".so");

		return shlib;
	}

	/**
	 * 指定したファイルを削除する
	 * 
	 * @param file
	 */
	public static void deleteFile(File file) {
		if (file != null && file.exists()) {
			if (!file.delete()) {
				System.err.println("Failed to delete file:");
				System.err.println(file.toString());
			}
		}
	}

//	/**
//	 * Test Plan作成用ファイルリストを作成する
//	 * <p>
//	 * tmpfilelistに要素が追加されたら、クライアントは使用後テンポラリファイルを削除する必要がある。
//	 * 
//	 * @param sources
//	 *            元のリソースのリスト (ここから必要なリソースを選択しFileにする)
//	 * @param filelist
//	 *            Test Plan作成に必要なファイルのリスト(クライアントがListを作成して渡す)
//	 * @param tmpfilelist
//	 *            作成されたテンポラリファイルのリスト (クライアントがListを作成して渡す)
//	 * @throws IOException
//	 *             テンポラリファイルの作成時にthrowされる可能性がある
//	 */
//	public static void createTestplanFileList(IResource[] sources,
//			List<File> filelist, List<File> tmpfilelist) throws IOException {
//		Assert.isNotNull(sources);
//		Assert.isNotNull(filelist);
//		Assert.isNotNull(tmpfilelist);
//
//		// Header用のテンポラリファイルを用意する
//		File headertmp = File.createTempFile("tde", ".hdr");
//		tmpfilelist.add(headertmp);
//		filelist.add(headertmp);
//
//		// Job Specファイルをリストの最後に追加するため
//		File jobSpecFile = null;
//
//		// リストを作成する
//		for (IResource resource : sources) {
//			String name = resource.getName();
//			if (SuffixMapper.isHeaderFile(name)) {
//				// Header (Propertyの設定値を考慮しテンポラリファイルに書き出す)
//				TdeHeaderUtil.updateHeaderFile(resource.getLocation().toFile(),
//						headertmp);
//			} else if (SuffixMapper.isJobspec(name)) {
//				// Job Spec
//				jobSpecFile = resource.getLocation().toFile();
//			} else if (SuffixMapper.isTestplanSpecFile(name)) {
//				// その他の Spec
//				filelist.add(resource.getLocation().toFile());
//			}
//		}
//
//		// 最後にJobSpecを追加する
//		if (jobSpecFile != null) {
//			filelist.add(jobSpecFile);
//		}
//	}
//
//	/**
//	 * AlgorithmLibraryファイル作成のため、与えられたリソースから ファイルのリストを返す
//	 * 
//	 * @param AlgorithmLibraryを作成するために必要なファイルのリスト
//	 *            リストの正当性(必要なファイルがそろっているかどうか)は確認しない。 リストの正当性は別のメソッドで確認可能。
//	 * 
//	 * @return Fileのリスト(nullは返さない), AlgorithmLibraryファイルは、
//	 *         リストの順番にファイルを結合することで作成する。
//	 */
//	public static List<File> toAlgorithmLibFileList(IResource[] list) {
//
//		List<File> retlist = new ArrayList<File>();
//
//		if (list != null) {
//			File aheader = null; // Algorithm Library用ヘッダーファイル
//
//			for (int i = 0; i < list.length; i++) {
//				IResource resource = list[i];
//				if (resource != null) {
//					String name = resource.getName();
//					if (name != null
//							&& SuffixMapper.isAlgorithmHeaderFile(name)) {
//						// アルゴリズムライブラリ用ヘッダーファイル
//						aheader = resource.getLocation().toFile();
//					} else if (SuffixMapper.isAlglibSpecExtension(resource
//							.getFileExtension())) {
//						retlist.add(resource.getLocation().toFile());
//					}
//				}
//			}
//
//			// ヘッダーファイルを最初に追加する
//			if (aheader != null) {
//				retlist.add(0, aheader);
//			}
//		}
//
//		return retlist;
//	}

//	/**
//	 * 与えられたリソースのリストから、Algorithm Specだけのファイルリストを作成して返す
//	 * 
//	 * @param list
//	 *            入力リスト
//	 * @return Algorithm Specファイルのリスト
//	 */
//	public static List<File> toAlgSpecFileList(IResource[] list) {
//
//		ArrayList<File> retarray = new ArrayList<File>();
//
//		if (list != null) {
//			for (int i = 0; i < list.length; i++) {
//				IResource resource = list[i];
//
//				if (resource != null) {
//					String name = resource.getName();
//					if (SuffixMapper.isAlgspec(name)) {
//						retarray.add(resource.getLocation().toFile());
//					}
//				}
//			}
//		}
//
//		return retarray;
//	}

//	/**
//	 * 与えられたIResourceの配列が、Testplan作成のためにvalidかどうかを返す
//	 * 
//	 * @param list
//	 *            Testplanを構成するSpecリソースのリスト、SYSTEM.hdrおよびJob Specを 含んでいなければならない
//	 * 
//	 * @return ヘッダーファイルおよびJob Specファイルがあればtrueを返す
//	 */
//	public static boolean isValidListForTestplan(IResource[] list) {
//
//		boolean existheader = false;
//		boolean existjobspec = false;
//
//		if (list != null && list.length > 0) {
//			for (int i = 0; i < list.length; i++) {
//				IResource resource = list[i];
//				if (resource != null) {
//					String name = resource.getName();
//					if (name != null && SuffixMapper.isHeaderFile(name)
//							&& name.compareTo(HEADER_NAME) == 0) {
//						// ヘッダーファイルはHeaderFileNameでなければならない
//						existheader = true;
//					} else if (name != null && SuffixMapper.isJobspec(name)
//							&& name.compareTo(JOBSPEC_NAME) == 0) {
//						// JobスペックはJobSpecFileNameでなければならない
//						existjobspec = true;
//					}
//				}
//			}
//		}
//
//		if (!existheader) {
//			System.out.println("Warning: SYSTEM.hdr file is missing.");
//		}
//		if (!existjobspec) {
//			System.out.println("Warning: SYSTEM.job file is missing.");
//		}
//
//		return existheader && existjobspec;
//	}
//
//	/**
//	 * 与えられたIResourceの配列が、Algorithm Libraryファイル作成のためにvalidかどうかを返す
//	 * 
//	 * @param list
//	 *            Algorithm Libraryファイルを構成するSpecリソースのリスト 　
//	 *            　ヘッダーファイルを含んでいなければならない
//	 * 
//	 * @return ヘッダーファイルがあればtrueを返す
//	 * 
//	 *         bugzilla 2640対応で .algが一つもない Projectでの lib fileの生成を許容する
//	 */
//	public static boolean isValidListForAlgLib(IResource[] list) {
//		if (list != null && list.length > 0) {
//			for (int i = 0; i < list.length; i++) {
//				IResource resource = list[i];
//				if (resource != null) {
//					String name = resource.getName();
//					if (name != null
//							&& SuffixMapper.isAlgorithmHeaderFile(name)
//							&& name.compareTo(ALGORITHM_HEADER_NAME) == 0) {
//						// ヘッダーファイルは既定の名前でなければならない
//						return true;
//					}
//				}
//			}
//		}
//
//		return false;
//	}

//	/**
//	 * 与えられたファイルが.cppファイルの自動生成をともなうAlgorithm Specであれば trueを返す
//	 * <p>
//	 * trueを返すのはfileが以下の条件をすべて満たした場合である
//	 * <ul>
//	 * <li>ファイルの拡張子が.algである</li>
//	 * <li>ファイルは、Algorithm Library用のnatureがセットされているプロジェクトの直下に存在する</li>
//	 * </ul>
//	 * 
//	 * @param file
//	 * @return
//	 */
//	public static boolean isManagedAlgorithmFile(IFile file) {
//		if (file != null) {
//			if (SuffixMapper.isAlgspec(file.getName())) {
//				IContainer container = file.getParent();
//				if (container instanceof IProject) {
//					IProject project = (IProject) container;
//					if (NatureTools.isAlgorithmLibraryProject(project)) {
//						return true;
//					}
//				}
//			}
//		}
//
//		return false;
//	}

//	/**
//	 * 与えられたEditorInputが.cppファイルの自動生成をともなうAlgorithm Specであれば trueを返す
//	 * 
//	 * @param input
//	 * @return
//	 */
//	public static boolean isManagedAlgorithmFile(IEditorInput input) {
//		IFile file = ResourceTools.getFileFromEditorInput(input);
//		if (file != null) {
//			return isManagedAlgorithmFile(file);
//		}
//
//		return false;
//	}

//	/**
//	 * Algorithm Spec名から、対応する.cppファイルを返す ファイルが存在しない場合は、編集中のAlgorithm
//	 * Specの内容からファイルを作成する
//	 * 
//	 * @param project
//	 *            Algorithm Specの所属するプロジェクト
//	 * @param specname
//	 *            Algorithm Spec名
//	 * @return
//	 */
//	public static IFile getCppFile(IProject project, String specname) {
//
//		IContainer folder = getUserSourceFolder(project);
//		Path filepath = new Path(specname + ".cpp");
//		IFile cppfile = folder.getFile(filepath);
//
//		if (!cppfile.exists()) {
//			// .cppファイルが存在しないので作成する
//			createCppFileFromEditingSpecOrFile(project, specname);
//		}
//
//		return cppfile;
//	}

	/**
	 * 与えられたfolderがuser sourceフォルダであればtrueを返す
	 * 
	 * @param folder
	 * @return
	 */
	public static boolean isUserSourceFolder(IContainer folder) {

		boolean result = false;

		if (folder != null) {
			IProject project = folder.getProject();
			if (project != null) {
				IContainer userfolder = getUserSourceFolder(project);
				if (userfolder != null) {
					return userfolder.equals(folder);
				}
			}
		}

		return result;
	}

//	/**
//	 * プロジェクト内のテストプランファイルを更新する
//	 * <p>
//	 * エディタで編集中のリソースがあれば、エディタで編集状態のデータを用いてファイルを更新する
//	 * 
//	 * @param project
//	 *            テストプランファイルを更新するプロジェクト
//	 */
//	public static void updateTestplanFileInProject(IProject project) {
//		updateTestplansWithEditorInputs(project, true);
//	}

//	/**
//	 * build実行前に呼ばれる
//	 * 
//	 * 指定されたプロジェクトにおいて、以下をおこなう 1) .algファイルから.cppファイルを作り直す 2)
//	 * すべての.exe.cppファイルを作り直す
//	 * 
//	 * @param project
//	 *            AlgorithmLibraryプロジェクト
//	 */
//	public static void updateAlgorithmProject(IProject project) {
//
//		// Algorithm Library(.lib)ファイルをプロジェクト内に作成する
//		TdeResourceUtil.writeAlgLibfile(project, null);
//
//		IContainer cppfolder = getUserSourceFolder(project);
//		IPath clibpath = cppfolder.getLocation();
//		ImportWizardTools.importCorCppFiles(clibpath, project);
//	}
//
//	/**
//	 * 与えられたプロジェクトのファイルが編集中であれば、それらをsaveする
//	 * 
//	 * 現在はTestplanプロジェクトのリソースを扱うエディタのみsaveをおこなうが、
//	 * IEditorOperationsを実装したエディタであれば、saveの対象とすることができる
//	 * 
//	 * @note ファイルのsaveをおこなう際はTdeResourceMonitorはdisableされる
//	 * 
//	 * @param project
//	 *            テストプランプロジェクト
//	 * 
//	 */
//	public static void saveEditingFilesOfProject(IProject project) {
//
//		// 開いているエディタのリストを取得する
//		IEditorReference[] editors = TdeResourceUtil2.getEditorReferences();
//		if (editors != null) {
//
//			// 一時的にResourceのモニタリングをdisableする
//			boolean previous = TdeManager.getInstance()
//					.setResourceChangeMonitorEnabled(false);
//			try {
//				for (IEditorReference editor : editors) {
//					if (editor != null) {
//						try {
//							IEditorInput editorinput = editor.getEditorInput();
//							if (contains(project, editorinput)) {
//								IEditorPart editorpart = editor
//										.getEditor(false);
//								if (editorpart instanceof IEditorOperations) {
//									((IEditorOperations) editorpart).save();
//								}
//							}
//						} catch (PartInitException e) {
//							e.printStackTrace();
//						}
//					}
//				}
//			} finally {
//				// 元の状態に戻す
//				TdeManager.getInstance().setResourceChangeMonitorEnabled(
//						previous);
//			}
//		}
//	}
//
//	/**
//	 * リソースが編集中であればsaveする
//	 * 
//	 * @param resource
//	 */
//	public static void saveResource(IResource resource) {
//		// 開いているエディタのリストを取得する
//		IEditorReference[] editors = PlatformUI.getWorkbench()
//				.getActiveWorkbenchWindow().getActivePage()
//				.getEditorReferences();
//
//		for (int i = 0; i < editors.length; i++) {
//			IEditorReference editor = editors[i];
//			try {
//				IEditorInput editorinput = editor.getEditorInput();
//				if (editorinput instanceof IFileEditorInput) {
//					IFileEditorInput fileinput = (IFileEditorInput) editorinput;
//					IFile file = fileinput.getFile();
//					if (file.equals(resource)) {
//						IEditorPart editorpart = editor.getEditor(false);
//						if (editorpart instanceof IEditorOperations) {
//							// editorに対してsaveを実行させる
//							((IEditorOperations) editorpart).save();
//						}
//					}
//				}
//			} catch (PartInitException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	/**
//	 * リソースを編集しているエディタをcloseする
//	 * 
//	 * @param resource
//	 */
//	public static void closeEditor(IResource resource) {
//		// 開いているエディタのリストを取得する
//		IEditorReference[] editors = getEditorReferences();
//
//		for (int i = 0; i < editors.length; i++) {
//			IEditorReference editor = editors[i];
//			try {
//				IEditorInput editorinput = editor.getEditorInput();
//				if (editorinput instanceof IFileEditorInput) {
//					IFileEditorInput fileinput = (IFileEditorInput) editorinput;
//					IFile file = fileinput.getFile();
//					if (file.equals(resource)) {
//						IEditorPart editorpart = editor.getEditor(false);
//						if (editorpart != null) {
//							if (editorpart.isDirty()) {
//								// dirty flagを消す
//								if (editorpart instanceof AbstractSpecTableEditor) {
//									((AbstractSpecTableEditor) editorpart)
//											.getTableModel().getDataModel()
//											.clearDirty();
//								}
//							}
//
//							// Editorをcloseする
//							if (editorpart instanceof IEditorOperations) {
//								((IEditorOperations) editorpart).close(false);
//							}
//						} else {
//							closeEditorReference(editor);
//						}
//					}
//				}
//			} catch (PartInitException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	/**
//	 * 指定したプロジェクトについて、エディタで変更中のメンバーが存在すればtrueを返す
//	 * 
//	 * @param project
//	 */
//	public static boolean isDirty(IProject project) {
//
//		// 開いているエディタのリストを取得する
//		IEditorReference[] editors = PlatformUI.getWorkbench()
//				.getActiveWorkbenchWindow().getActivePage()
//				.getEditorReferences();
//
//		for (int i = 0; i < editors.length; i++) {
//			IEditorReference editor = editors[i];
//			try {
//				IEditorInput editorinput = editor.getEditorInput();
//				if (contains(project, editorinput)) {
//					IEditorPart editorpart = editor.getEditor(false);
//					if (editorpart != null && editorpart.isDirty()) {
//						return true;
//					} else {
//						return false;
//					}
//				}
//			} catch (PartInitException e) {
//				e.printStackTrace();
//			}
//		}
//
//		return false;
//	}
//
//	/**
//	 * 指定したファイルについて、エディタで変更中のメンバーが存在すればtrueを返す
//	 * 
//	 * @param project
//	 */
//	public static boolean isDirty(IFile file) {
//
//		// 開いているエディタのリストを取得する
//		IEditorReference[] editors = PlatformUI.getWorkbench()
//				.getActiveWorkbenchWindow().getActivePage()
//				.getEditorReferences();
//
//		for (int i = 0; i < editors.length; i++) {
//			IEditorReference editor = editors[i];
//			try {
//				IEditorInput editorinput = editor.getEditorInput();
//				if (!(editorinput instanceof IFileEditorInput))
//					continue;
//
//				if (((IFileEditorInput) editorinput).getFile().equals(file)) {
//					IEditorPart editorpart = editor.getEditor(false);
//					if (editorpart != null && editorpart.isDirty()) {
//						return true;
//					} else {
//						return false;
//					}
//				}
//			} catch (PartInitException e) {
//				e.printStackTrace();
//			}
//		}
//
//		return false;
//	}
//
//	/**
//	 * 指定したファイルのダーティフラグをクリアする
//	 * 
//	 * @param file
//	 */
//	public static void clearDirty(IFile file) {
//
//		// 開いているエディタのリストを取得する
//		IEditorReference[] editors = PlatformUI.getWorkbench()
//				.getActiveWorkbenchWindow().getActivePage()
//				.getEditorReferences();
//
//		for (int i = 0; i < editors.length; i++) {
//			IEditorReference editor = editors[i];
//			try {
//				IEditorInput editorinput = editor.getEditorInput();
//				if (!(editorinput instanceof IFileEditorInput))
//					continue;
//
//				if (((IFileEditorInput) editorinput).getFile().equals(file)) {
//					IEditorPart editorpart = editor.getEditor(false);
//					// Editorの Tabがあっても実際に開かれていない場合には editorpart=nullになる
//					// その場合 Dirty Flagは立っていないのでここで Return
//					// ( TDE Close時の状態を再現するのみでインスタンス化されていない場合 )
//					if (editorpart == null)
//						return;
//					if (editorpart.isDirty()) {
//						if (editorpart instanceof AbstractSpecTableEditor) {
//							((AbstractSpecTableEditor) editorpart)
//									.getTableModel().getDataModel()
//									.clearDirty();
//							return;
//						}
//					}
//				}
//			} catch (PartInitException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	/**
//	 * Testplan Explorerに表示されうるすべての Resourceのダーティフラグをクリアする
//	 * 
//	 */
//	public static void clearDirtyAll() {
//
//		// 開いているエディタのリストを取得する
//		IEditorReference[] editors = PlatformUI.getWorkbench()
//				.getActiveWorkbenchWindow().getActivePage()
//				.getEditorReferences();
//
//		for (int i = 0; i < editors.length; i++) {
//			IEditorReference editor = editors[i];
//			try {
//				IEditorInput editorinput = editor.getEditorInput();
//				if (!(editorinput instanceof IFileEditorInput))
//					continue;
//
//				IProject project = ((IFileEditorInput) editorinput).getFile()
//						.getProject();
//				if (NatureTools.isTestplanFileProject(project)
//						|| NatureTools.isTcoFileProject(project)
//						|| NatureTools.isLimitFileProject(project)) {
//					IEditorPart editorpart = editor.getEditor(false);
//					if (editorpart != null && editorpart.isDirty()) {
//						if (editorpart instanceof AbstractSpecTableEditor) {
//							((AbstractSpecTableEditor) editorpart)
//									.getTableModel().getDataModel()
//									.clearDirty();
//						}
//					}
//				}
//			} catch (PartInitException e) {
//				e.printStackTrace();
//			}
//		}
//	}

//	/**
//	 * プロジェクトがTestPlanファイルの内容から変更されていたらtrueを返す
//	 * 
//	 * @param project
//	 * @param testplanPath
//	 *            比較するTestPlanファイル
//	 * @return
//	 */
//	public static boolean isModifiedFromTestPlan(IProject project,
//			String testplanPath) {
//		Assert.isNotNull(project);
//
//		if (!StringTools.isSetAny(testplanPath)) {
//			// testplanPathがセットされていない
//			return true;
//		}
//
//		File base = new File(testplanPath);
//		if (!base.exists()) {
//			// TestPlanファイルが存在しない
//			return true;
//		}
//
//		// 編集中のリソースをsaveする
//		// TdeResourceUtil.saveEditingFilesOfProject(project); //bugzilla
//		// 2993対応でコメントアウト
//
//		// プロジェクトが作成したTestPlanファイルを取得する
//		IFile projectTestplan = TdeResourceUtil.getTestplan(project, true);
//		Assert.isNotNull(projectTestplan);
//
//		String path1 = testplanPath;
//		String path2 = projectTestplan.getLocation().toString();
//
//		// 比較結果を返す
//		return !isSameTestPlanFiles(path1, path2);
//
//	}

	/**
	 * 指定したファイルを編集中のエディタを表示する ( ただし Focusは Editorには移動しない )
	 * 
	 * @param file
	 */
	public static void activateEditor(IFile file) {

		// Pageを取得する
		IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();

		// 開いているエディタのリストを取得する
		IEditorReference[] editors = page.getEditorReferences();

		for (int i = 0; i < editors.length; i++) {
			IEditorReference editor = editors[i];
			try {
				IEditorInput editorinput = editor.getEditorInput();
				if (editorinput instanceof IFileEditorInput) {
					if (((IFileEditorInput) editorinput).getFile().equals(file)) {
						// fileを編集中のエディタなのでこれをactivateする
						IWorkbenchPart part = editor.getPart(true);
						page.bringToTop(part);
						// page.activate(part); // Focus移動しない
						break;
					}
				}
			} catch (PartInitException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 与えられたIEditorInputが所属するプロジェクトを返す
	 * 
	 * @param input
	 * @return
	 */
	public static IProject getProjectFromEditorInput(IEditorInput input) {
		if (input instanceof FileEditorInput) {
			return ((FileEditorInput) input).getFile().getProject();
		} else {
			return null;
		}
	}

	/**
	 * 与えられたeditor inputのリソースを返す
	 * 
	 * @param input
	 * @return リソースまたはnull
	 */
	public static IResource getResourceFromEditorInput(IEditorInput input) {
		if (input instanceof IFileEditorInput) {
			IResource resource = (IResource) (((IFileEditorInput) input)
					.getFile());
			return resource;
		}

		return null;
	}

//	/**
//	 * アクティブなエディタが編集しているリソースが属するプロジェクトを返す
//	 * 
//	 * @return プロジェクトまたはnullを返す
//	 */
//	public static IResource getProjectOrMemberFromActiveEditorInput() {
//		IProject project = null;
//
//		// アクティブなエディタを取得する
//		IWorkbench workbench = PlatformUI.getWorkbench();
//		IWorkbenchPart part = workbench.getActiveWorkbenchWindow()
//				.getActivePage().getActiveEditor();
//
//		if (part instanceof AbstractSpecEditor
//				|| part instanceof WaferSpecEditor) {
//			IEditorInput input = ((IEditorPart) part).getEditorInput();
//			project = TdeResourceUtil.getProjectFromEditorInput(input);
//			if (NatureTools.isTestplanFileProject(project))
//				return project;
//			if (NatureTools.isTcoFileProject(project)
//					|| NatureTools.isLimitFileProject(project)) {
//				if (input instanceof IFileEditorInput) {
//					return ((IFileEditorInput) input).getFile();
//				} else {
//					return null;
//				}
//			}
//		}
//
//		return null;
//	}

//	/**
//	 * Testplanプロジェクトにテンプレートファイルを追加する
//	 * 
//	 * @param projectName
//	 * @return 成功するとtrueを返す
//	 */
//	public static boolean addTemplateSpecs(final String projectName) {
//
//		IRunnableWithProgress op = new IRunnableWithProgress() {
//
//			@Override
//			public void run(IProgressMonitor monitor)
//					throws InvocationTargetException, InterruptedException {
//				File hdrTmpfile = null;
//				File jobTmpfile = null;
//				try {
//					hdrTmpfile = createHeaderFile(projectName);
//					createResourceFromFile(hdrTmpfile, projectName,
//							TdeResourceUtil.HEADER_NAME, monitor, true);
//					jobTmpfile = createJobSpecFile();
//					createResourceFromFile(jobTmpfile, projectName,
//							TdeResourceUtil.JOBSPEC_NAME, monitor, true);
//				} catch (IOException e) {
//					System.err.println("failed to create temporary spec file");
//				} catch (CoreException e) {
//					throw new InvocationTargetException(e);
//				} finally {
//					TdeResourceUtil.deleteFile(hdrTmpfile);
//					TdeResourceUtil.deleteFile(jobTmpfile);
//					monitor.done();
//				}
//			}
//		};
//
//		try {
//			PlatformUI.getWorkbench().getProgressService()
//					.run(false, false, op);
//		} catch (InterruptedException e) {
//			return false;
//		} catch (InvocationTargetException e) {
//			Throwable realException = e.getTargetException();
//
//			Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
//					.getShell();
//			MessageDialog.openError(shell, "Error", realException.getMessage());
//
//			return false;
//		}
//
//		return true;
//	}

	/**
	 * 指定されたファイルからリソースを作成する
	 * 
	 * @param sourceFile
	 * @param containerName
	 * @param fileName
	 * @param monitor
	 * @throws CoreException
	 * @throws CoreException
	 * @throws IOException
	 */
	public static IFile createResourceFromFile(File sourceFile,
			String containerName, String fileName, IProgressMonitor monitor,
			final boolean openEditor) throws CoreException, IOException {

		monitor.beginTask("Creating " + fileName, 2);

		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource resource = root.findMember(new Path(containerName));

		if (!resource.exists() || !(resource instanceof IContainer)) {
			throwCoreException("Container \"" + containerName
					+ "\" does not exist.");
		}

		IContainer container = (IContainer) resource;
		final IFile file = container.getFile(new Path(fileName));
		InputStream stream = null;

		stream = new FileInputStream(sourceFile);
		if (file.exists()) {
			file.setContents(stream, true, true, monitor);
		} else {
			file.create(stream, true, monitor);
		}
		stream.close();

		monitor.worked(1);
		monitor.setTaskName("Opening file for editing...");

		getShell().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				if (openEditor) {
					ResourceTools.openEditor(file);
				}
			}
		});

		monitor.worked(1);
		return file;
	}

	/**
	 * Displayもしくはnullを返す
	 * 
	 * @return
	 */
	public static Display getDisplay() {
		IWorkbench wb = PlatformUI.getWorkbench();
		if (wb != null) {
			return wb.getDisplay();
		} else {
			return null;
		}
	}

	/**
	 * Shellもしくはnullを返す
	 * 
	 * @return
	 */
	public static Shell getShell() {
		IWorkbench wb = PlatformUI.getWorkbench();
		if (wb != null) {
			IWorkbenchWindow window = wb.getActiveWorkbenchWindow();
			if (window != null) {
				return window.getShell();
			}
		}

		return null;
	}

//	/**
//	 * テンプレート用JobSpecを作成する
//	 * 
//	 * @return　作成したJobSpec
//	 */
//	public static void setupTemplateJobSpec(JobSpec spec) {
//
//		fillSpecificaitonData(spec, "SYSTEM");
//
//		JobBody body;
//		body = new JobBody();
//		body.setCommand("SELECT");
//		body.setParameter("WAFER,\"WaferSpecName\"");
//		spec.addBodyData(body);
//
//		body = new JobBody();
//		body.setCommand("SELECT");
//		body.setParameter("PROBE,\"ProbeSpecName\"");
//		spec.addBodyData(body);
//
//		body = new JobBody();
//		body.setCommand("TEST");
//		body.setParameter("\"DieSpecName\",\"TestSpecName\"");
//		spec.addBodyData(body);
//
//		body = new JobBody();
//		body.setCommand("LOOP");
//		spec.addBodyData(body);
//	}

//	/**
//	 * TestplanHeader用のテンプレートSpec情報をセットする
//	 * 
//	 * user preferenceが保存されていれば、これを使う。 なければ、sysconfの情報を使用する
//	 * 
//	 * @param spec
//	 * @param projectName
//	 */
//	public static void setupTemplateHeader(TestplanHeader spec,
//			String projectName) {
//
//		fillSpecificaitonData(spec, projectName);
//		spec.setDescription("Description");
//
//		setMeasurementLibraries(spec);
//		setTesterLibrary(spec);
//		setProberLibrary(spec);
//		setUtilityLibrary(spec);
//		setFramework(spec);
//	}

	/**
	 * 　与えられたリソースがSYSTEM.hdrならtrueを返す
	 * 
	 * @param resource
	 * @return
	 */
	public static boolean isSystemHeader(IResource resource) {
		if (resource != null && !(resource instanceof IProject)) {
			String name = resource.getName();
			if (name.compareTo(HEADER_NAME) == 0) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 与えられたリソースがSYSTEM.jobならtrueを返す
	 * 
	 * @param resource
	 * @return
	 */
	public static boolean isSystemJob(IResource resource) {
		if (resource != null && !(resource instanceof IProject)) {
			String name = resource.getName();
			if (name.compareTo(JOBSPEC_NAME) == 0) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 与えられたファイルからFileStoreEdtorInputを作成して返す
	 * 
	 * @param path
	 *            ファイルのパス
	 * @return 作成したFileStoreEdtorInput
	 */
	public static FileStoreEditorInput getFileStoreEditorInput(IPath path) {
		Assert.isNotNull(path);

		IFileSystem fileSystem = EFS.getLocalFileSystem();
		IFileStore fileStore = fileSystem.getStore(path);
		FileStoreEditorInput input = new FileStoreEditorInput(fileStore);

		return input;
	}

//	/**
//	 * IEditorReferenceのリストを配列で返す
//	 * 
//	 * @return
//	 */
//	private static IEditorReference[] getEditorReferences() {
//		List<IEditorReference> result = new ArrayList<IEditorReference>();
//
//		IWorkbench workbench = PlatformUI.getWorkbench();
//		if (workbench != null) {
//			IWorkbenchWindow[] windows = workbench.getWorkbenchWindows();
//			for (int i = 0; i < windows.length; i++) {
//				IWorkbenchWindow window = windows[i];
//				if (window != null) {
//					IWorkbenchPage page = window.getActivePage();
//					if (page != null) {
//						IEditorReference[] editors = page.getEditorReferences();
//						List<IEditorReference> list = ListOperations
//								.getListOf(editors);
//						result.addAll(list);
//					}
//				}
//			}
//		}
//
//		return result.toArray(new IEditorReference[result.size()]);
//	}
//
//	/**
//	 * IEditorReferenceのリストを配列で返す
//	 * 
//	 * @return
//	 */
//	private static void closeEditorReference(final IEditorReference closeeditor) {
//		IWorkbench workbench = PlatformUI.getWorkbench();
//		if (workbench != null) {
//			IWorkbenchWindow[] windows = workbench.getWorkbenchWindows();
//			for (int i = 0; i < windows.length; i++) {
//				IWorkbenchWindow window = windows[i];
//				if (window != null) {
//					final IWorkbenchPage page = window.getActivePage();
//					if (page != null) {
//						IEditorReference[] editors = page.getEditorReferences();
//						List<IEditorReference> list = ListOperations
//								.getListOf(editors);
//						if (list.contains(closeeditor)) {
//							Display display = PlatformUI.getWorkbench()
//									.getDisplay();
//							display.asyncExec(new Runnable() {
//								@Override
//								public void run() {
//									page.closeEditors(
//											new IEditorReference[] { closeeditor },
//											true);
//								}
//							});
//							break;
//						}
//					}
//				}
//			}
//		}
//	}
//
//	/**
//	 * Measurement Libraries User Preferenceが保存されていればその値を、そうでなければsysconfの値を使用する
//	 * 
//	 * @param spec
//	 */
//	private static void setMeasurementLibraries(TestplanHeader spec) {
//
//		// UserPreferenceにセットされているライブラリのリストを取得
//		String measlibs = TestPlanHeaderPreferences.getMeasurementLibs();
//
//		int numadded = 0;
//		if (StringTools.isSetAny(measlibs)) {
//			String trimmedText = measlibs.trim();
//			String[] results = trimmedText.split(",", -1);
//			for (int i = 0; i < results.length; i++) {
//				String lib = results[i];
//				if (StringTools.isSetAny(lib)) {
//					LibraryData data = new LibraryData();
//					data.setName(lib);
//					data.setKind(LibraryKind.MEASURE);
//					data.setDescription("Algorithms");
//					spec.addLibrary(data);
//					numadded++;
//				}
//			}
//		}
//
//		if (numadded <= 0) {
//			LibraryData data = new LibraryData();
//			data.setName(SysConf.getMeasurementLibrary());
//			data.setKind(LibraryKind.MEASURE);
//			data.setDescription("Algorithms");
//			spec.addLibrary(data);
//		}
//	}
//
//	/**
//	 * Tester Library
//	 * 
//	 * @param spec
//	 */
//	private static void setTesterLibrary(TestplanHeader spec) {
//		LibraryData data = new LibraryData();
//		data.setKind(LibraryKind.TESTER);
//		data.setDescription("Tester Algorithms");
//
//		String lib = TestPlanHeaderPreferences.getTesterLib();
//		if (StringTools.isSetAny(lib)) {
//			data.setName(lib);
//		} else {
//			data.setName(SysConf.getTesterLibrary());
//		}
//		spec.addLibrary(data);
//	}
//
//	/**
//	 * Prober Library
//	 * 
//	 * @param spec
//	 */
//	private static void setProberLibrary(TestplanHeader spec) {
//		LibraryData data = new LibraryData();
//		data.setKind(LibraryKind.PROBER);
//		data.setDescription("Prober Algorithms");
//
//		String lib = TestPlanHeaderPreferences.getProberLib();
//		if (StringTools.isSetAny(lib)) {
//			data.setName(lib);
//		} else {
//			data.setName(SysConf.getProberLibrary());
//		}
//		spec.addLibrary(data);
//	}
//
//	/**
//	 * Utility Library
//	 * 
//	 * @param spec
//	 */
//	private static void setUtilityLibrary(TestplanHeader spec) {
//		LibraryData data = new LibraryData();
//		data.setKind(LibraryKind.UTILITY);
//		data.setDescription("Utility Algorithms");
//
//		String lib = TestPlanHeaderPreferences.getUtilityLib();
//		if (StringTools.isSetAny(lib)) {
//			data.setName(lib);
//		} else {
//			data.setName(SysConf.getUtilityLibrary());
//		}
//		spec.addLibrary(data);
//	}
//
//	/**
//	 * Framework
//	 * 
//	 * @param spec
//	 */
//	private static void setFramework(TestplanHeader spec) {
//		LibraryData data = new LibraryData();
//		data.setKind(LibraryKind.FRAMEWORK);
//		data.setDescription("Framework");
//
//		String lib = TestPlanHeaderPreferences.getFramework();
//		if (StringTools.isSetAny(lib)) {
//			data.setName(lib);
//		} else {
//			data.setName(SysConf.getFramework());
//		}
//		spec.addLibrary(data);
//	}
//
//	/**
//	 * 与えられたテストプランが等しいかどうか比較する
//	 * 
//	 * @param path1
//	 *            テストプランのパス
//	 * @param path2
//	 *            テストプランのパス
//	 * @return 等しければtrue, そうでなければfalseを返す
//	 */
//	private static boolean isSameTestPlanFiles(String path1, String path2) {
//		TestPlanReader reader1 = new TestPlanReader(path1);
//		TestPlanReader reader2 = new TestPlanReader(path2);
//		TestPlan testplan1 = new TestPlan();
//		TestPlan testplan2 = new TestPlan();
//		try {
//			reader1.read(testplan1);
//			reader2.read(testplan2);
//			return testplan1.isSame(testplan2);
//		} catch (IOException e) {
//			return false;
//		} catch (SpecParseException e) {
//			e.showMessage();
//			return false;
//		}
//	}
//
//	/**
//	 * JobSpecのテンプレートをテンポラリファイルに書き出し、ファイルを返す
//	 * 
//	 * @param spec
//	 * @return
//	 * @throws IOException
//	 */
//	private static File createJobSpecFile() throws IOException {
//
//		// テンプレートスペックを作成する
//		JobSpec spec = new JobSpec();
//		setupTemplateJobSpec(spec);
//
//		// ファイルに書き出す
//		File tmpfile = File.createTempFile("tdenew", null);
//		JobSpecWriter writer = new JobSpecWriter(tmpfile.getPath());
//		writer.write(spec);
//
//		return tmpfile;
//	}
//
//	/**
//	 * Headerをテンポラリファイルに書き出し、ファイルを返す
//	 * 
//	 * @param spec
//	 * @return
//	 * @throws IOException
//	 */
//	private static File createHeaderFile(String projectName) throws IOException {
//
//		// テンプレートスペックを作成する
//		TestplanHeader header = new TestplanHeader();
//		setupTemplateHeader(header, projectName);
//
//		// ファイルに書き出す
//		File tmpfile = File.createTempFile("tdenew", null);
//		TestplanHeaderWriter writer = new TestplanHeaderWriter(
//				tmpfile.getPath());
//		writer.write(header);
//
//		return tmpfile;
//	}
//
//	private static void fillSpecificaitonData(Specification spec, String name) {
//		// 名前をセット
//		spec.setName(name);
//		// 日付をセット
//		String datestr = StringTools.getCurrentDateString();
//		spec.setDate(datestr);
//
//		// 時間をセット
//		String timestr = StringTools.getCurrentTimeString();
//		spec.setTime(timestr);
//
//		// ユーザー名をセット
//		spec.setUser(StringTools.getUserName());
//	}

	private static void throwCoreException(String message) throws CoreException {
		IStatus status = new Status(IStatus.ERROR,
				"com.agilent.p9000.specs.tde", IStatus.OK, message, null);

		throw new CoreException(status);
	}

	/**
	 * 　必要に応じてプロジェクトにソースファイルを格納するためのフォルダを作成する
	 * 
	 * @param project
	 */
	private static void assertSourceFolders(IProject project) {

		// プロジェクト直下にsrc/usr, src/sysフォルダがなければ作成する
		IResource src = project.findMember(SOURCE_FOLDER_NAME);
		if (src == null) {
			// なければ作成する
			src = project.getFolder(SOURCE_FOLDER_NAME);
		}

		if (src instanceof IContainer) {
			IResource user = ((IContainer) src).findMember(USER_FOLDER_NAME);
			if (user == null) {
				// 作成する "src/usr"
				user = project.getFolder(SOURCE_FOLDER_NAME + "/"
						+ USER_FOLDER_NAME);
			}

			IResource system = ((IContainer) src)
					.findMember(SYSTEM_FOLDER_NAME);
			if (system == null) {
				// 作成する "src/sys"
				system = project.getFolder(SOURCE_FOLDER_NAME + "/"
						+ SYSTEM_FOLDER_NAME);
			}
		} else {
			System.err.println("failed to create src folder");
		}
	}

	private static void assertBinaryFolders(IProject project) {

		// プロジェクト直下にDebugフォルダがなければ作成する
		IResource folder = project.findMember(BINARY_FOLDER_NAME);
		if (folder == null) {
			// なければ作成する
			folder = project.getFolder(BINARY_FOLDER_NAME);
		}
	}

	/**
	 * 指定した名前のファイルがEditorInputが所属するプロジェクトに存在すれば、ファイルを返す
	 * 
	 * @param input
	 *            プロジェクトを指定するためのEditorInput
	 * @param filename
	 *            取得するファイルのファイル名
	 * @return
	 */
	private static IFile getFileFromEditorInput(IEditorInput input,
			String filename) {
		if (input instanceof FileEditorInput) {
			FileEditorInput fileinput = (FileEditorInput) input;
			IFile file = fileinput.getFile();
			if (file != null) {
				try {
					IResource[] members = file.getProject().members();
					for (int i = 0; i < members.length; i++) {
						IResource member = members[i];
						if (member.getName().compareTo(filename) == 0) {
							return (IFile) member;
						}
					}
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}

	/**
	 * エディタインプットがプロジェクトに所属していればtrueを返す
	 * 
	 * @param in1
	 * @param in2
	 * @return
	 */
	private static boolean contains(IProject project, IEditorInput input) {
		if (project == null || !(input instanceof FileEditorInput)) {
			return false;
		}

		FileEditorInput finput = (FileEditorInput) input;
		if (project.equals(finput.getFile().getProject())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 与えられたFileのリストのうち、テンポラリファイルで置き換えるものがあれば置き換える。
	 * 置き換えた場合はテンポラリファイル名とエディタの名前のmapを更新する
	 * 
	 * @param list
	 *            入力ファイルリスト
	 * @param map
	 *            keyはSpecファイル, valueはテンポラリファイル
	 * @param namemap
	 * @return 置き換えられたリスト
	 */
	private static List<File> createSpecFileList(List<File> list,
			Map<File, File> map, Map<String, String> namemap) {
		List<File> retlist = new ArrayList<File>();

		for (int i = 0; i < list.size(); i++) {
			File path = list.get(i);
			if (path != null) {
				// テンポラリファイルが存在するので戻り値のリストに追加する
				File replaced = map.get(path);
				if (replaced != null) {
					retlist.add(replaced);
					namemap.put(replaced.getName(), path.getName());
				} else {
					retlist.add(path);
				}
			}
		}

		return retlist;
	}

//	/**
//	 * .testplan.tplxファイルを作成する
//	 * 
//	 * @param project
//	 *            プロジェクト
//	 * @param specs
//	 *            Test Planファイルのソースとして使用するスペックファイルのリスト
//	 * @param namemap
//	 *            テンポラリファイル名とスペックファイル名のMap, スペック名を取得するために参照される
//	 * @param shell
//	 *            (nullでも可)
//	 */
//	private static void writeTestPlanFile(IProject project, List<File> specs,
//			Map<String, String> namemap, final Shell shell) {
//
//		File tmpfile = null;
//		try {
//			tmpfile = File.createTempFile("tdetpl", null);
//			IRunnableWithProgress op = TplExporter.getRunnableForWritingSpecs(
//					specs, namemap, tmpfile);
//			writeTestplanCommon(project, op, TdeResourceUtil.TESTPLAN_NAME,
//					tmpfile, shell);
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (tmpfile != null && tmpfile.exists()) {
//				deleteFile(tmpfile);
//			}
//		}
//	}

	/**
	 * テストプランファイルを作成する
	 * 
	 * @param project
	 *            プロジェクト
	 * @param op1
	 *            テンポラリファイルに出力するためのoperation
	 * @param outputFileName
	 *            出力ファイル名 (e.g. .testplan.tplx)
	 * @param tmpfile
	 *            テンポラリファイル
	 * @param shell
	 *            Shellまたはnull
	 */
	private static void writeTestplanCommon(IProject project,
			IRunnableWithProgress op1, String outputFileName,
			final File tmpfile, final Shell shell) {

		try {
			PlatformUI.getWorkbench().getProgressService()
					.run(false, false, op1);
		} catch (InterruptedException e) {
			return;
		} catch (InvocationTargetException e) {
			System.err.println(e.getTargetException().getMessage());
			e.printStackTrace();
		}

		// ファイルハンドルを取得する(ファイルがプロジェクトに無ければ作成する)
		final IFile outputfile = project.getFile(outputFileName);
		IRunnableWithProgress op2 = new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException, InterruptedException {
				InputStream tplcontent = null;
				try {
					tplcontent = new FileInputStream(tmpfile);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

				try {
					writeToResource(tplcontent, outputfile, monitor, shell);
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (tplcontent != null) {
						try {
							tplcontent.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		};

		try {
			PlatformUI.getWorkbench().getProgressService()
					.run(false, false, op2);
		} catch (InterruptedException e) {
			return;
		} catch (InvocationTargetException e) {
			System.err.println(e.getTargetException().getMessage());
			e.printStackTrace();
		}
	}

//	/**
//	 * パラメータで与えられたプロジェクトに存在する.algファイルから"プロジェクト名.lib" ファイルを作成する。
//	 * 
//	 * @param project
//	 *            プロジェクト
//	 * @param specs
//	 *            入力として使用するスペックファイルのリスト
//	 * @param namemap
//	 *            スペック名を取得するために使用する テンポラリファイル名とスペックファイル名のMap
//	 * @param shell
//	 *            (nullでも可)
//	 */
//	private static void writeAlgLibfile(final IProject project,
//			final List<File> specs, final Map<String, String> namemap,
//			final Shell shell) {
//
//		// Algorithm Libraryを書き出すテンポラリファイル
//		File tmpfile = null;
//
//		try {
//			tmpfile = File.createTempFile("tdelib", null);
//			IRunnableWithProgress alglibop = null;
//
//			alglibop = AlgExporter.getRunnableForWritingSpecs(specs, namemap,
//					tmpfile);
//
//			try {
//				PlatformUI.getWorkbench().getProgressService()
//						.run(false, false, alglibop);
//			} catch (InterruptedException e) {
//				return;
//			} catch (InvocationTargetException e) {
//				System.err.println(e.getTargetException().getMessage());
//				e.printStackTrace();
//			}
//
//			// プロジェクト内にリソースを作成するためにファイルハンドルを取得する
//			// (リソースがプロジェクトに無ければ作成される)
//			String projectName = project.getName();
//			final IFile alglibfile = project.getFile(BINARY_FOLDER_PATH + "/"
//					+ projectName + "." + ALGLIB_EXTENSION);
//
//			final File ftmpfile = tmpfile;
//			IRunnableWithProgress op = new IRunnableWithProgress() {
//				@Override
//				public void run(IProgressMonitor monitor)
//						throws InvocationTargetException, InterruptedException {
//					InputStream tplcontent = null;
//					try {
//						tplcontent = new FileInputStream(ftmpfile);
//					} catch (FileNotFoundException e) {
//						e.printStackTrace();
//					}
//
//					try {
//						writeToResource(tplcontent, alglibfile, monitor, shell);
//					} catch (IOException e) {
//						e.printStackTrace();
//					} finally {
//						if (tplcontent != null) {
//							try {
//								tplcontent.close();
//							} catch (IOException e) {
//								e.printStackTrace();
//							}
//						}
//					}
//				}
//			};
//
//			try {
//				PlatformUI.getWorkbench().getProgressService()
//						.run(false, false, op);
//			} catch (InterruptedException e) {
//				return;
//			} catch (InvocationTargetException e) {
//				System.err.println(e.getTargetException().getMessage());
//				e.printStackTrace();
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			deleteFile(tmpfile);
//		}
//	}

//	/**
//	 * 与えられたプロジェクトのTest Planファイルを更新する。
//	 * 
//	 * @param project
//	 *            対象プロジェクト
//	 * @param checkEditors
//	 *            trueならば編集中のエディタの内容を用いてTest Planファイルを作成する
//	 */
//	private static void updateTestplansWithEditorInputs(IProject project,
//			boolean checkEditors) {
//		final IProject proj = project;
//		final boolean check = checkEditors;
//		Display display = getDisplay();
//		display.syncExec(new Runnable() {
//			@Override
//			public void run() {
//				// エディタのインプットファイルとテンポラリファイルパスのmap
//				Map<File, File> tmpfiles = new HashMap<File, File>();
//				if (check) {
//					// 開いているエディタのリストを取得する
//					IEditorReference[] editors = PlatformUI.getWorkbench()
//							.getActiveWorkbenchWindow().getActivePage()
//							.getEditorReferences();
//
//					// 編集中のデータをテンポラリファイルに書き出す(dirty flagは無視して、常に書き出す)
//					for (int i = 0; i < editors.length; i++) {
//						IEditorReference editor = editors[i];
//						try {
//							IEditorInput editorinput = editor.getEditorInput();
//
//							if (contains(proj, editorinput)) {
//								if (editorinput instanceof FileEditorInput) {
//									IPath path = ((FileEditorInput) editorinput)
//											.getPath();
//									IEditorPart editorpart = editor
//											.getEditor(false);
//									if (editorpart instanceof ITestplanExecutor) {
//										File file = ((ITestplanExecutor) editorpart)
//												.saveToTemporaryFile();
//										if (file != null) {
//											tmpfiles.put(path.toFile(), file);
//										}
//									}
//								}
//							}
//						} catch (PartInitException e) {
//							e.printStackTrace();
//						}
//					}
//				}
//
//				List<File> filelist = new ArrayList<File>();
//				List<File> tmpfilelist = new ArrayList<File>();
//				try {
//					// プロジェクトに属するスペックファイルとヘッダーを取得する
//					TdeManager.getInstance().getSpecsAndHeader(proj, filelist,
//							tmpfilelist);
//
//					// テンポラリファイル名と実際のリソース名のmap
//					Map<String, String> namemap = new HashMap<String, String>();
//
//					// specsをテンポラリファイルで置き換えたリストを作成する
//					List<File> speclist = createSpecFileList(filelist,
//							tmpfiles, namemap);
//
//					// speclistからtestplanを作成する
//					writeTestPlanFile(proj, speclist, namemap, PlatformUI
//							.getWorkbench().getActiveWorkbenchWindow()
//							.getShell());
//
//				} catch (CoreException e) {
//					e.printStackTrace();
//				} catch (IOException e) {
//					e.printStackTrace();
//				} finally {
//					// テンポラリファイルを削除する
//					Collection<File> files = tmpfiles.values();
//					for (File file : files) {
//						deleteFile(file);
//					}
//					for (File file : tmpfilelist) {
//						deleteFile(file);
//					}
//				}
//			}
//		});
//	}
//
//	/**
//	 * 与えられたプロジェクト, Spec名用の.cppファイルを作成する
//	 * 
//	 * @param project
//	 * @param specname
//	 */
//	private static void createCppFileFromEditingSpecOrFile(IProject project,
//			String specname) {
//
//		// 開いているエディタのリストを取得する
//		IEditorReference[] editors = PlatformUI.getWorkbench()
//				.getActiveWorkbenchWindow().getActivePage()
//				.getEditorReferences();
//
//		File tmpfile = null; // テンポラリファイル
//
//		for (int i = 0; i < editors.length; i++) {
//			IEditorReference editor = editors[i];
//			IEditorPart editorpart = editor.getEditor(true);
//			if (editorpart instanceof AlgorithmSpecEditor) {
//				// editorpartはAlgorithm Spec Editorである
//				try {
//					IEditorInput editorinput = editor.getEditorInput();
//					if (contains(project, editorinput)
//							&& editorinput instanceof FileEditorInput) {
//						FileEditorInput finput = (FileEditorInput) editorinput;
//						IPath inputpath = finput.getPath();
//						String inputname = inputpath.toFile().getName();
//						if (inputname.compareTo(specname + ".alg") == 0) {
//							// 編集対象がbasename.algファイルである
//							tmpfile = ((IAlgorithmExecutor) editorpart)
//									.saveToTemporaryFile();
//							break;
//						}
//					}
//				} catch (PartInitException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		if (tmpfile != null) {
//			AlgorithmSpec spec = new AlgorithmSpec();
//			AlgorithmSpecReader reader = new AlgorithmSpecReader(
//					tmpfile.getPath());
//			try {
//				// テンポラリファイルから読み込む
//				reader.read(spec);
//
//				// テンポラリファイルから読み込んだ場合Spec名がテンポラリファイル名になるので、
//				// 正しい名前をセットする
//				spec.setName(specname);
//
//				IContainer folder = getUserSourceFolder(project);
//				ImportWizardTools.writeAlgCppFile(spec, specname, folder, null);
//			} catch (IOException e) {
//				e.printStackTrace();
//			} catch (SpecParseException e) {
//				e.showMessage();
//				e.printStackTrace();
//			} finally {
//				deleteFile(tmpfile);
//			}
//		}
//
//	}
//
//	/**
//	 * 指定されたspecに初期値を入れる
//	 * 
//	 * @param name
//	 * @param spec
//	 */
//	private static void setupSpecification(String name, AlgorithmHeader spec) {
//
//		// 名前をセット
//		spec.setName(name);
//
//		// 日付をセット
//		String datestr = StringTools.getCurrentDateString();
//		spec.setDate(datestr);
//
//		// 時間をセット
//		String timestr = StringTools.getCurrentTimeString();
//		spec.setTime(timestr);
//
//		// ユーザー名をセット
//		spec.setUser(StringTools.getUserName());
//
//		// description
//		spec.setDescription("Description");
//	}
//
//	/**
//	 * TCOプロジェクトに 空のTCOファイルを追加する
//	 * 
//	 * @param projectName
//	 * @return 成功するとtrueを返す
//	 */
//	public static boolean addTcoFile(final String projectName,
//			final String sourcePath, final String fileName) {
//
//		IRunnableWithProgress op = new IRunnableWithProgress() {
//
//			@Override
//			public void run(IProgressMonitor monitor)
//					throws InvocationTargetException, InterruptedException {
//				File srcfile = null;
//				String propertyTargetPath = null;
//				try {
//					if (StringTools.isSetAny(sourcePath)) {
//						srcfile = new File(sourcePath);
//						propertyTargetPath = sourcePath;
//					} else {
//						srcfile = createTcoFile();
//						propertyTargetPath = null;
//					}
//					IFile file = createResourceFromFile(srcfile, projectName,
//							fileName, monitor, true);
//					TestplanOperations.setTargetPath(file, propertyTargetPath);
//				} catch (IOException e) {
//					System.err.println("failed to create temporary tco file");
//				} catch (CoreException e) {
//					throw new InvocationTargetException(e);
//				} finally {
//					if (!StringTools.isSetAny(sourcePath))
//						TdeResourceUtil.deleteFile(srcfile);
//					monitor.done();
//				}
//			}
//		};
//
//		try {
//			PlatformUI.getWorkbench().getProgressService()
//					.run(false, false, op);
//		} catch (InterruptedException e) {
//			return false;
//		} catch (InvocationTargetException e) {
//			Throwable realException = e.getTargetException();
//
//			Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
//					.getShell();
//			MessageDialog.openError(shell, "Error", realException.getMessage());
//
//			return false;
//		}
//
//		return true;
//	}
//
//	/**
//	 * 空 TCOをテンポラリファイルに書き出し、ファイルを返す
//	 * 
//	 * @param spec
//	 * @return
//	 * @throws IOException
//	 */
//	private static File createTcoFile() throws IOException {
//
//		// テンプレートスペックを作成する
//		TestplanConstant tco = new TestplanConstant();
//
//		// ファイルに書き出す
//		File tmpfile = File.createTempFile("tdenew", null);
//		TestplanConstantWriter writer = new TestplanConstantWriter(
//				tmpfile.getPath());
//		writer.write(tco);
//
//		return tmpfile;
//	}
//
//	/**
//	 * TCOプロジェクトに 空のLimit(limx)ファイルを追加する
//	 * 
//	 * @param projectName
//	 * @return 成功するとtrueを返す
//	 */
//	public static boolean addLimitFile(final String projectName,
//			final String sourcePath, final String fileName) {
//
//		IRunnableWithProgress op = new IRunnableWithProgress() {
//
//			@Override
//			public void run(IProgressMonitor monitor)
//					throws InvocationTargetException, InterruptedException {
//				File srcfile = null;
//				String propertyTargetPath = null;
//				try {
//					if (StringTools.isSetAny(sourcePath)) {
//						srcfile = new File(sourcePath);
//						if (sourcePath.endsWith(".limx"))
//							propertyTargetPath = sourcePath;
//						else
//							propertyTargetPath = null; // limx以外は
//						// Targetとしてリンクしない
//					} else {
//						srcfile = createLimitFile();
//						propertyTargetPath = null;
//					}
//					IFile file = createResourceFromFile(srcfile, projectName,
//							fileName, monitor, true);
//					TestplanOperations.setTargetPath(file, propertyTargetPath);
//				} catch (IOException e) {
//					System.err.println("failed to create limit file");
//				} catch (CoreException e) {
//					throw new InvocationTargetException(e);
//				} finally {
//					if (!StringTools.isSetAny(sourcePath))
//						TdeResourceUtil.deleteFile(srcfile);
//					monitor.done();
//				}
//			}
//		};
//
//		try {
//			PlatformUI.getWorkbench().getProgressService()
//					.run(false, false, op);
//		} catch (InterruptedException e) {
//			return false;
//		} catch (InvocationTargetException e) {
//			Throwable realException = e.getTargetException();
//
//			Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
//					.getShell();
//			MessageDialog.openError(shell, "Error", realException.getMessage());
//
//			return false;
//		}
//
//		return true;
//	}
//
//	/**
//	 * 空 TCOをテンポラリファイルに書き出し、ファイルを返す
//	 * 
//	 * @param spec
//	 * @return
//	 * @throws IOException
//	 */
//	private static File createLimitFile() throws IOException {
//
//		// テンプレートスペックを作成する
//		TestplanLimit tco = new TestplanLimit();
//
//		// ファイルに書き出す
//		File tmpfile = File.createTempFile("tdenew", null);
//		TestplanLimitWriter writer = new TestplanLimitWriter(tmpfile.getPath());
//		writer.write(tco);
//
//		return tmpfile;
//	}
//
//	/**
//	 * Project TCO/Limit Fileを Target Fileにコピーする ( SaveAs用 )
//	 * 
//	 * @param spec
//	 * @return
//	 * @throws IOException
//	 */
//	public static void updateTargetFile(IFile file) {
//		IProject project = file.getProject();
//		if (NatureTools.isTcoFileProject(project)
//				|| NatureTools.isLimitFileProject(project)) {
//
//			String target = TestplanOperations.getTargetPath(file);
//			if (!StringTools.isSetAny(target))
//				return;
//
//			// LimitFile Project以下の Limx Fileで Targetが指定されている場合は
//			// Project以下の Limx Fileを Targetにコピー
//			try {
//				// file.copy(new Path(target), true, new NullProgressMonitor());
//				IFileSystem fileSystem = EFS.getLocalFileSystem();
//				IFileStore fsSrc = fileSystem.getStore(file.getLocation());
//				IFileStore fsDst = fileSystem.getStore(new Path(target));
//				fsSrc.copy(fsDst, EFS.OVERWRITE, new NullProgressMonitor());
//			} catch (CoreException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	/**
//	 * To prevent instantiation
//	 */
//	private TdeResourceUtil() {
//		Assert.isTrue(false, "this class should not be instantiated");
//	}
//
//	/**
//	 * 以下は Update/Goto XXX機能実装で利用される Utility
//	 */
//	public static boolean showEditorAndSelectRow(final IFile file,
//			final SpecDataContainer body) {
//
//		if (file == null)
//			return true;
//
//		// 検索に時間がかかる。既に開いている Editorに対して IDE.openEditorを実行すれば
//		// 同様の効果があるので探す Codeは削除された
//		//
//		// Editorを開く
//		Display display = PlatformUI.getWorkbench().getDisplay();
//		display.syncExec(new Runnable() {
//
//			@Override
//			public void run() {
//				IEditorPart editor = ResourceTools.openEditorPart(file);
//				if (body != null) {
//					if (editor instanceof AbstractSpecTableEditor) {
//						AbstractSpecTableEditor tseditor = (AbstractSpecTableEditor) editor;
//						tseditor.getTableModel().selectRow(body);
//					} else if (editor instanceof AbstractTdeMultiPageEditor) {
//						AbstractTdeMultiPageEditor mpeditor = (AbstractTdeMultiPageEditor) editor;
//						// 現状 AbstractTdeMultiPageEditorは Wafer Specのみ、
//						// その Wafer Specでは getSpecEditorでは Body
//						// Editorが返されるのでこの方法を採用
//						// 将来的に getSpecEditorがどのPageを返すかの規定はないので要注意
//						AbstractSpecTableEditor tseditor = (AbstractSpecTableEditor) mpeditor
//								.getSpecEditor();
//						mpeditor.setActiveEditor(tseditor);
//						tseditor.getTableModel().selectRow(body);
//					}
//				}
//			}
//		});
//
//		return true;
//	}
//
//	// SELECT PROBE,<probe spec>を検索して Projectの対応 Probe Spec名を返す
//	public static String getRelatedProbeSpec(IProject project) {
//		IResource jobfile = project.findMember(TdeResourceUtil.JOBSPEC_NAME);
//		if (jobfile == null)
//			return "";
//		AbstractDataManager jobdatamanager = JobSpecProvider.getInstance()
//				.getDataManager(jobfile.getLocation());
//		JobSpec jobspec = (JobSpec) jobdatamanager.getSpec();
//		JobBodyTable jobspectable = jobspec.getBodyTable();
//
//		for (int i = 0; i < jobspectable.getNumLinesToSave(); i++) {
//			JobBody body = (JobBody) jobspectable.getContainer(i);
//			if (!body.isDisabled()) {
//				String command = body.getCommand();
//				if (command != null && command.trim().equals("SELECT")) {
//					String[] params = ReaderWriterTools
//							.extractJobSelectParameters(body.getParameter());
//					if (params[0].equals("PROBE")) {
//						IResource resource = project.findMember(params[1]
//								+ ".prb");
//						if (!(resource instanceof IFile))
//							continue;
//
//						return params[1];
//					}
//				}
//			}
//		}
//		return "";
//	}
//
//	// SELECT WAFER,<wafer spec>を検索して Projectの対応 Wafer Spec名を返す
//	public static String getRelatedWaferSpec(IProject project) {
//		IResource jobfile = project.findMember(TdeResourceUtil.JOBSPEC_NAME);
//		if (jobfile == null)
//			return "";
//		AbstractDataManager jobdatamanager = JobSpecProvider.getInstance()
//				.getDataManager(jobfile.getLocation());
//		JobSpec jobspec = (JobSpec) jobdatamanager.getSpec();
//		JobBodyTable jobspectable = jobspec.getBodyTable();
//
//		for (int i = 0; i < jobspectable.getNumLinesToSave(); i++) {
//			JobBody body = (JobBody) jobspectable.getContainer(i);
//			if (!body.isDisabled()) {
//				String command = body.getCommand();
//				if (command != null && command.trim().equals("SELECT")) {
//					String[] params = ReaderWriterTools
//							.extractJobSelectParameters(body.getParameter());
//					if (params[0].equals("WAFER")) {
//						IResource resource = project.findMember(params[1]
//								+ ".waf");
//						if (!(resource instanceof IFile))
//							continue;
//
//						return params[1];
//					}
//				}
//			}
//		}
//		return "";
//	}
//
//	public static List<Pair<String, String>> getDieTestPair(IProject project) {
//		List<Pair<String, String>> list = new ArrayList<Pair<String, String>>();
//		// Target Project以下の TSTX Fileの検索
//		IResource jobfile = project.findMember(TdeResourceUtil.JOBSPEC_NAME);
//		if (jobfile == null)
//			return list;
//		AbstractDataManager jobdatamanager = JobSpecProvider.getInstance()
//				.getDataManager(jobfile.getLocation());
//		JobSpec jobspec = (JobSpec) jobdatamanager.getSpec();
//		JobBodyTable jobspectable = jobspec.getBodyTable();
//
//		for (int i = 0; i < jobspectable.getNumLinesToSave(); i++) {
//			JobBody body = (JobBody) jobspectable.getContainer(i);
//			if (!body.isDisabled()) {
//				String command = body.getCommand();
//				if (command != null && command.trim().equals("TEST")) {
//					String[] params = ReaderWriterTools
//							.extractJobSelectParameters(body.getParameter());
//					IResource resource = project
//							.findMember(params[1] + ".tstx");
//					if (!(resource instanceof IFile))
//						continue;
//
//					list.add(new Pair<String, String>(params[0], params[1]));
//				}
//			}
//		}
//		return list;
//	}

//	public static String[] getMeasurePath(String sourcepath) {
//		IPath projectpath = new Path(sourcepath);
//		IPath specFilePath = projectpath.append(TdeResourceUtil.HEADER_NAME);
//		FileStoreEditorInput input = TdeResourceUtil
//				.getFileStoreEditorInput(specFilePath);
//
//		TestplanHeaderDataModel datamodel = new TestplanHeaderDataModel(input);
//		TestplanHeaderModelProxy modelProxy = new TestplanHeaderModelProxy(
//				datamodel);
//
//		List<String> libs = modelProxy.getMeasurementLibs();
//		datamodel.dispose();
//
//		List<String> mlist = new ArrayList<String>();
//		for (String name : libs) {
//			if (!StringTools.isSetAny(name))
//				continue;
//			Path path = new Path(name);
//			if (path.isAbsolute())
//				mlist.add(path.toOSString());
//			else
//				mlist.add(new Path("C:/ProgramData/SPECSX/usr/cppalg").append(path)
//						.toOSString());
//		}
//		return mlist.toArray(new String[mlist.size()]);
//	}

//	public static String[] getLimitPath(String sourcepath) {
//		IPath projectpath = new Path(sourcepath);
//		IPath specFilePath = projectpath.append(TdeResourceUtil.HEADER_NAME);
//		FileStoreEditorInput input = TdeResourceUtil
//				.getFileStoreEditorInput(specFilePath);
//
//		TestplanHeaderDataModel datamodel = new TestplanHeaderDataModel(input);
//		TestplanHeaderModelProxy modelProxy = new TestplanHeaderModelProxy(
//				datamodel);
//
//		String limitName = modelProxy.getLimit();
//		datamodel.dispose();
//
//		if (!StringTools.isSetAny(limitName))
//			return new String[0];
//		String[] ret = new String[1];
//		Path name = new Path(limitName + ".limx");
//		if (name.isAbsolute())
//			ret[0] = name.toOSString();
//		else
//			ret[0] = new Path("C:/ProgramData/SPECSX/usr/lim").append(name).toOSString();
//		return ret;
//	}

//	public static String[] getTcoPath(String sourcepath) {
//		IPath projectpath = new Path(sourcepath);
//		IPath specFilePath = projectpath.append(TdeResourceUtil.HEADER_NAME);
//		FileStoreEditorInput input = TdeResourceUtil
//				.getFileStoreEditorInput(specFilePath);
//
//		TestplanHeaderDataModel datamodel = new TestplanHeaderDataModel(input);
//		TestplanHeaderModelProxy modelProxy = new TestplanHeaderModelProxy(
//				datamodel);
//
//		String tcoName = modelProxy.getTco();
//		datamodel.dispose();
//
//		if (!StringTools.isSetAny(tcoName))
//			return new String[0];
//		String[] ret = new String[1];
//		Path name = new Path(tcoName + ".tco");
//		if (name.isAbsolute())
//			ret[0] = name.toOSString();
//		else
//			ret[0] = new Path("C:/ProgramData/SPECSX/usr/tco").append(name).toOSString();
//		return ret;
//	}
}
