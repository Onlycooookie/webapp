@WebServlet("/index")
public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 检查文件索引是否存在
        Object fileIndex = this.getServletContext().getAttribute("fileIndex");
        if (fileIndex == null) {
            // 获取可用的驱动器路径列表
            List<String> list = FileReaderUtil.getAvailableDrivePaths();
            req.getSession().setAttribute("driveList", list);
            req.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(req, resp);
        } else {
            // 如果文件索引已存在，则重定向到搜索页面
            resp.sendRedirect(req.getContextPath() + "/search");
        }
    }
