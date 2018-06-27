package tools;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.IPage;
import org.eclipse.ui.part.PageBookView;

public class TdeResourceUtil2 {

//	public static IFile getActiveTestplan() {
//		IFile testplanfile = null;
//
//		IWorkbenchPart part = getActivePart();
//		IEditorInput input = TdeResourceUtil2.getEditorInput(part);
//
//		if (input != null) {
//			// エディタがアクティブで、Test Planファイルを取得することができた
//			testplanfile = TdeResourceUtil.getTestplan(input);
//		} else {
//			// エディタがアクティブでないので選択状態からTest Planファイルを取得する
//			ISelection selection = getSelectionOfActivePage();
//			if (selection instanceof IStructuredSelection) {
//				Set<IProject> projects = TdeResourceUtil2
//						.getSelectedTestplanProjects(selection);
//
//				if (projects.size() == 1) {
//					// ただ一つ選択されているので実行可能
//					for (IProject project : projects) {
//						testplanfile = TdeResourceUtil.getTestplan(project,
//								true);
//						break;
//					}
//				}
//			}
//		}
//
//		return testplanfile;
//	}

	/**
	 * 与えられたeditor inputのリソースまたはnullを返す
	 * 
	 * @param editorInput
	 * @return editor inputのリソースまたはnull
	 */
	public static IResource getResoueOfEditorInput(IEditorInput editorInput) {
		if (editorInput instanceof IFileEditorInput) {
			IFileEditorInput fileinput = (IFileEditorInput) editorInput;

			return (IResource) fileinput.getFile();
		}
		return null;
	}

//	/**
//	 * アクティブなTestSpecEditorを返す
//	 * <p>
//	 * Test Spec Editorに関連づけられているOutlineView, AssistViewがアクティブの場合、 Test Spec
//	 * Editorを返す
//	 * 
//	 * @return TestSpecEditorまたはnull
//	 */
//	public static TestSpecEditor getActiveTestSpecEditor() {
//		return getTestSpecEditor(getActivePart());
//	}

	/**
	 * EditorReferenceまたはnullを返す
	 * 
	 * @return EditorReferenceまたはnull
	 */
	public static IEditorReference[] getEditorReferences() {
		try {
			IEditorReference[] references = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage()
					.getEditorReferences();
			return references;
		} catch (NullPointerException e) {
			return null;
		}
	}

	/**
	 * ActivePartを返す
	 * 
	 * @return ActivePartまたはnull
	 */
	public static IWorkbenchPart getActivePart() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench != null) {
			try {
				IWorkbenchPart part = workbench.getActiveWorkbenchWindow()
						.getActivePage().getActivePart();
				return part;
			} catch (NullPointerException e) {
				return null;
			}
		}

		return null;
	}

	/**
	 * アクティブなエディタを返す
	 * <p>
	 * Active Partがエディタであればそれを返すし、エディタでなければnullを返す
	 * 
	 * @return エディタまたはnull
	 */
	public static IEditorPart getActiveEditorPart() {
		IWorkbenchPart part = getActivePart();
		if (part instanceof IEditorPart) {
			IEditorPart editor = (IEditorPart) part;
			return editor;
		}

		return null;
	}

	/**
	 * 最前面に位置するエディタを返す
	 * 
	 * @return エディタでまたはnull
	 */
	public static IEditorPart getTopEditorPart() {
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow[] windows = wb.getWorkbenchWindows();

		for (IWorkbenchWindow workbenchWindow : windows) {
			IWorkbenchPage[] wpages = workbenchWindow.getPages();
			for (IWorkbenchPage workbenchPart : wpages) {
				return workbenchPart.getActiveEditor();
			}
		}

		return null;
	}

	/**
	 * ActivePageのselectionを返す
	 * 
	 * @return selectionまたはnull
	 */
	public static ISelection getSelectionOfActivePage() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench != null) {
			try {
				ISelection selection = workbench.getActiveWorkbenchWindow()
						.getActivePage().getSelection();
				return selection;
			} catch (NullPointerException e) {
				return null;
			}
		}

		return null;
	}

	/**
	 * 与えられたeditorで編集中のリソースが属するprojectを返す
	 * 
	 * @return projectまたはnull
	 */
	public static IProject getEditorsProject(IEditorPart editor) {
		if (editor != null) {
			IEditorInput editorInput = editor.getEditorInput();
			IFile file = ResourceTools.getFileFromEditorInput(editorInput);
			if (file != null) {
				return file.getProject();
			}
		}

		return null;
	}

	/**
	 * Activeなエディタで編集中のファイルを返す
	 * <p>
	 * Activeなエディタがない場合、ActiveなエディタがあってもIFileを編集していない場合はnullを返す
	 * 
	 * @return　ファイルまたはnull
	 */
	public static IFile getActiveEditorsFile() {
		IEditorPart editor = getActiveEditorPart();
		if (editor != null) {
			IEditorInput input = editor.getEditorInput();
			if (input instanceof IFileEditorInput) {
				IFileEditorInput fileinput = (IFileEditorInput) input;
				return fileinput.getFile();
			}
		}

		return null;
	}

	/**
	 * 最前面に位置するエディタが編集中のファイルを返す
	 * 
	 * @return IFileまたはnull
	 */
	public static IFile getTopEditorsFile() {
		IEditorPart editor = getTopEditorPart();
		if (editor != null) {
			IEditorInput input = editor.getEditorInput();
			if (input instanceof IFileEditorInput) {
				IFileEditorInput fileinput = (IFileEditorInput) input;
				return fileinput.getFile();
			}
		}

		return null;
	}

//	/**
//	 * 与えられたselectionで選択されているTestPlanプロジェクトのSetを返す
//	 * 
//	 * @param selection
//	 * @return 選択されているTest Planプロジェクトのリスト(空のリストは返してもnullは返さない)
//	 */
//	public static Set<IProject> getSelectedTestplanProjects(ISelection selection) {
//		Set<IProject> projects = new HashSet<IProject>();
//
//		if (selection instanceof IStructuredSelection) {
//			IStructuredSelection ss = (IStructuredSelection) selection;
//			Object[] items = ss.toArray();
//			for (Object item : items) {
//				if (item instanceof IResource) {
//					IProject project;
//					IResource resource = (IResource) item;
//					if (resource instanceof IProject) {
//						project = (IProject) resource;
//					} else {
//						project = resource.getProject();
//					}
//					if (NatureTools.isTestplanProject(project)) {
//						projects.add(project);
//					}
//				}
//			}
//		}
//
//		return projects;
//	}
//
//	/**
//	 * 与えられたselectionで選択されているAlgorithm LibraryプロジェクトのSetを返す
//	 * 
//	 * @param selection
//	 * @return 選択されているAlgorithm Libraryプロジェクトのリスト(空のリストは返してもnullは返さない)
//	 */
//	public static Set<IProject> getSelectedAlgorithmLibraryProjects(
//			ISelection selection) {
//		Set<IProject> projects = new HashSet<IProject>();
//
//		if (selection instanceof IStructuredSelection) {
//			IStructuredSelection ss = (IStructuredSelection) selection;
//			Object[] items = ss.toArray();
//			for (Object item : items) {
//				if (item instanceof IResource) {
//					IResource resource = (IResource) item;
//					IProject project;
//					if (resource instanceof IProject) {
//						project = (IProject) resource;
//					} else {
//						project = resource.getProject();
//					}
//					if (NatureTools.isAlgorithmLibraryProject(project)) {
//						projects.add(project);
//					}
//				}
//			}
//		}
//
//		return projects;
//	}
//
//	/**
//	 * 選択されているTCOファイルのリストを取得する
//	 * 
//	 * @param selection
//	 * @return
//	 */
//	public static Set<IFile> getSelectedTcoFiles(ISelection selection) {
//		Set<IFile> files = new HashSet<IFile>();
//
//		if (selection instanceof IStructuredSelection) {
//			IStructuredSelection ss = (IStructuredSelection) selection;
//			Object[] items = ss.toArray();
//			for (Object obj : items) {
//				if (obj instanceof IFile) {
//					IFile file = (IFile) obj;
//					if (SuffixMapper.isTcoFile(file.getName())) {
//						files.add(file);
//					}
//				}
//			}
//		}
//
//		return files;
//	}
//
//	public static Set<IFile> getSelectedLimitFiles(ISelection selection) {
//		Set<IFile> files = new HashSet<IFile>();
//
//		if (selection instanceof IStructuredSelection) {
//			IStructuredSelection ss = (IStructuredSelection) selection;
//			Object[] items = ss.toArray();
//			for (Object obj : items) {
//				if (obj instanceof IFile) {
//					IFile file = (IFile) obj;
//					if (SuffixMapper.isLimitFile(file.getName())) {
//						files.add(file);
//					}
//				}
//			}
//		}
//
//		return files;
//	}
//
//	/**
//	 * 指定されたPartがTestPlanProjectに属するファイル編集していればtrueを返す
//	 * 
//	 * @param part
//	 * @return
//	 */
//	public static boolean isTestPlanMemberEdited(IWorkbenchPart part) {
//		if (part instanceof IEditorPart) {
//			IFile file = TdeResourceUtil2.getActiveEditorsFile();
//			return isTestPlanProjectMember(file);
//		}
//
//		return false;
//	}
//
//	/**
//	 * 指定されたPartがAlgorithmLibraryに属するファイルを編集していればtrueを返す
//	 * 
//	 * @param part
//	 * @return
//	 */
//	public static boolean isAlgorithmMemberEdited(IWorkbenchPart part) {
//		if (part instanceof IEditorPart) {
//			IFile file = TdeResourceUtil2.getActiveEditorsFile();
//			if (file != null) {
//				IProject project = file.getProject();
//				if (NatureTools.isAlgorithmLibraryProject(project)) {
//					return true;
//				}
//			}
//		}
//
//		return false;
//	}
//
//	/**
//	 * 指定されたPartがTableNavigatorを保持していればtrueを返す
//	 * 
//	 * @param part
//	 * @return
//	 */
//	public static boolean isTableNavigator(IWorkbenchPart part) {
//		if (part instanceof PageBookView) {
//			PageBookView view = (PageBookView) part;
//			IPage page = view.getCurrentPage();
//			if (page instanceof TableNavigatorOutlinePage) {
//				return true;
//			}
//		}
//
//		return false;
//	}
//
//	/**
//	 * 指定されたPartがAlgorithmViewであり、Test Planプロジェクトのメンバーを扱っていたらtrueを返す
//	 * 
//	 * @param part
//	 * @return
//	 */
//	public static boolean isAlgorithmView(IWorkbenchPart part) {
//		if (part instanceof PageBookView) {
//			PageBookView view = (PageBookView) part;
//			IPage page = view.getCurrentPage();
//			if (page instanceof AlgorithmViewPage) {
//				return true;
//			}
//		}
//
//		return false;
//	}
//
//	/**
//	 * 指定されたPartがWaferMapを保持していればtrueを返す
//	 * 
//	 * @param part
//	 * @return
//	 */
//	public static boolean isWaferMapView(IWorkbenchPart part) {
//		if (part instanceof AssistView) {
//			AssistView view = (AssistView) part;
//			IPage page = view.getCurrentPage();
//			if (page instanceof WaferMapOutlinePage) {
//				return true;
//			}
//		}
//
//		return false;
//	}
//
//	/**
//	 * 与えられたpartがAssistViewの場合WaferMapOutlinePageを返す
//	 * 
//	 * @param part
//	 * @return WaferMapOutlinePageまたはnull
//	 */
//	public static WaferMapOutlinePage getWaferMapOutlinePage(IWorkbenchPart part) {
//		if (part instanceof AssistView) {
//			AssistView view = (AssistView) part;
//			IPage page = view.getCurrentPage();
//			if (page instanceof WaferMapOutlinePage) {
//				return (WaferMapOutlinePage) page;
//			}
//		}
//
//		return null;
//	}
//
//	/**
//	 * 与えられたpartがPageBookViewの場合TableNavigatorOutlinePageまたはnullを返す
//	 * 
//	 * @param part
//	 * @return
//	 */
//	public static TableNavigatorOutlinePage getTableNavigatorOutlinePage(
//			IWorkbenchPart part) {
//		if (part instanceof PageBookView) {
//			PageBookView view = (PageBookView) part;
//			IPage page = view.getCurrentPage();
//			if (page instanceof TableNavigatorOutlinePage) {
//				return (TableNavigatorOutlinePage) page;
//			}
//		}
//
//		return null;
//	}
//
//	/**
//	 * 与えられたpartがAssistViewの場合AlgorithmViewPageを返す
//	 * 
//	 * @param part
//	 * @return
//	 */
//	public static AlgorithmViewPage getAlgorithmViewPage(IWorkbenchPart part) {
//		if (part instanceof AssistView) {
//			AssistView view = (AssistView) part;
//			IPage page = view.getCurrentPage();
//			if (page instanceof AlgorithmViewPage) {
//				return (AlgorithmViewPage) page;
//			}
//		}
//		return null;
//	}
//
//	/**
//	 * 与えられたpartからEditorInputを取得して返す
//	 * <p>
//	 * <ul>
//	 * <li>partがIEditorPartのインスタンスであれば、そのeditor inputを返す</li>
//	 * <li>partがAssistViewであれば、そのマスターのエディタのeditor inputを返す</li>
//	 * <li>partがOutlineViewであれば、そのマスターのエディタのeditor inputを返す</li>
//	 * </ul>
//	 * 
//	 * @param part
//	 * @return editor inputまたはnull
//	 */
//	public static IEditorInput getEditorInput(IWorkbenchPart part) {
//		IEditorInput input = null;
//		if (part instanceof IEditorPart) {
//			// EditorPartの場合はそのままeditor inputを返す
//			input = ((IEditorPart) part).getEditorInput();
//		} else if (TdeResourceUtil2.isWaferMapView(part)) {
//			// AssistViewの場合はeditorのeditor inputを返す
//			WaferMapOutlinePage wafmap = getWaferMapOutlinePage(part);
//			if (wafmap != null) {
//				WaferSpecEditor editor = wafmap.getEditor();
//				if (editor != null) {
//					input = editor.getEditorInput();
//				}
//			}
//		} else if (TdeResourceUtil2.isAlgorithmView(part)) {
//			// AssistViewの場合はeditorのeditor inputを返す
//			AlgorithmViewPage algpage = getAlgorithmViewPage(part);
//			if (algpage != null) {
//				AbstractSpecTableEditor editor = algpage.getEditor();
//				if (editor != null) {
//					input = editor.getEditorInput();
//				}
//			}
//		} else if (TdeResourceUtil2.isTableNavigator(part)) {
//			// TableNavigatorの場合
//			TableNavigatorOutlinePage navipage = getTableNavigatorOutlinePage(part);
//			if (navipage != null) {
//				IEditorPart editor = navipage.getEditorPart();
//				if (editor != null) {
//					input = editor.getEditorInput();
//				}
//			}
//		}
//
//		return input;
//	}
//
//	/**
//	 * 与えられたpartからTestSpecEditorを取得して返す
//	 * <p>
//	 * <ul>
//	 * <li>partがIEditorPartのインスタンスであれば、partを返す</li>
//	 * <li>partがAlgorithmViewであれば、そのマスターのpartを返す</li>
//	 * <li>partがTableNavigatorであれば、そのマスターのpartを返す</li>
//	 * </ul>
//	 * 
//	 * @param part
//	 *            nullが与えられた場合nullを返す
//	 * @return editor inputまたはnull
//	 */
//	public static TestSpecEditor getTestSpecEditor(IWorkbenchPart part) {
//		TestSpecEditor result = null;
//		if (part instanceof TestSpecEditor) {
//			result = ((TestSpecEditor) part);
//		} else if (TdeResourceUtil2.isAlgorithmView(part)) {
//			AlgorithmViewPage algpage = getAlgorithmViewPage(part);
//			if (algpage != null) {
//				AbstractSpecTableEditor tableeditor = algpage.getEditor();
//				if (tableeditor instanceof TestSpecEditor) {
//					result = (TestSpecEditor) tableeditor;
//				}
//			}
//		} else if (TdeResourceUtil2.isTableNavigator(part)) {
//			TableNavigatorOutlinePage navipage = getTableNavigatorOutlinePage(part);
//			if (navipage != null) {
//				IEditorPart editorPart = navipage.getEditorPart();
//				if (editorPart instanceof TestSpecEditor) {
//					result = (TestSpecEditor) editorPart;
//				}
//			}
//		}
//
//		return result;
//	}
//
//	/**
//	 * 与えられたファイルがTest Planプロジェクトのメンバであればtrueを返す
//	 * 
//	 * @param file
//	 * @return
//	 */
//	public static boolean isTestPlanProjectMember(IFile file) {
//		if (file != null) {
//			IProject project = file.getProject();
//			if (NatureTools.isTestplanProject(project)) {
//				return true;
//			}
//		}
//
//		return false;
//	}
//
//	/**
//	 * 与えられたファイルがTCOプロジェクトのメンバであればtrueを返す
//	 * 
//	 * @param file
//	 * @return
//	 */
//	public static boolean isTcoProjectMember(IFile file) {
//		if (file != null) {
//			IProject project = file.getProject();
//			if (NatureTools.isTcoProject(project)) {
//				return true;
//			}
//		}
//
//		return false;
//	}
//
//	/**
//	 * 与えられたファイルがLimitプロジェクトのメンバであればtrueを返す
//	 * 
//	 * @param file
//	 * @return
//	 */
//	public static boolean isLimitProjectMember(IFile file) {
//		if (file != null) {
//			IProject project = file.getProject();
//			if (NatureTools.isLimitProject(project)) {
//				return true;
//			}
//		}
//
//		return false;
//	}

	/**
	 * editorをcloseする
	 * 
	 * @param editorinput
	 */
	public static void closeEditor(final IEditorInput editorinput) {
		try {
			IWorkbenchPage page = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage();
			IEditorPart editor = page.findEditor(editorinput);
			if (editor != null) {
				page.closeEditor(editor, false);
			}
		} catch (NullPointerException e) {
		}
	}
}
