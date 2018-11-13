package local.ldwx.web;

import local.ldwx.model.*;
import local.ldwx.storage.Storage;
import local.ldwx.util.Config;
import local.ldwx.util.DateUtil;
import local.ldwx.util.HtmlUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeServlet extends HttpServlet {

    private Storage storage;

    @Override
    public void init() throws ServletException {
        super.init();
        storage = Config.get().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter("uuid");
        String action = req.getParameter("action");
        if (action == null) {
            req.setAttribute("employees", storage.getAllSorted());
            req.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(req, resp);
            return;
        }
        Employee e;
        switch (action) {
            case ("delete"):
                storage.delete(uuid);
                resp.sendRedirect("employees");
                return;
            case ("view"):
                e = storage.get(uuid);
                break;
            case ("add"):
                e = Employee.EMPTY;
                break;
            case ("edit"):
                e = storage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    Section section = e.getSection(type);
                    switch (type) {
                        case OBJECTIVE:
                        case PERSONAL:
                            if (section == null) {
                                section = TextSection.EMPTY;
                            }
                            break;
                        case QUALIFICATIONS:
                            if (section == null) {
                                section = ListSection.EMPTY;
                            }
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            OrganizationSection organizationSection = (OrganizationSection) section;
                            List<Organization> emptyFirstOrganisations = new ArrayList<>();
                            emptyFirstOrganisations.add(Organization.EMPTY);
                            if (organizationSection != null) {
                                for (Organization org : organizationSection.getOrganizations()) {
                                    List<Organization.Position> emptyFirstPosition = new ArrayList<>();
                                    emptyFirstPosition.add(Organization.Position.EMPTY);
                                    emptyFirstPosition.addAll(org.getPositions());
                                    emptyFirstOrganisations.add(new Organization(org.getHomePage(), emptyFirstPosition));
                                }
                            }
                            section = new OrganizationSection(emptyFirstOrganisations);
                            break;
                    }
                    e.setSection(type, section);
                }
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        req.setAttribute("employee", e);
        req.getRequestDispatcher("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String uuid = req.getParameter("uuid");
        String fullName = req.getParameter("fullName");
        final boolean isCreate = (uuid == null || uuid.length() == 0);
        Employee e;
        if (isCreate) {
            e = new Employee(fullName);
        } else {
            e = storage.get(uuid);
            e.setFullName(fullName);
        }

        for (ContactType type : ContactType.values()) {
            String value = req.getParameter(type.name());
            if (HtmlUtil.isEmpty(value)) {
                e.getContacts().remove(type);
            } else {
                e.setContact(type, value);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = req.getParameter(type.name());
            String[] values = req.getParameterValues(type.name());
            if (HtmlUtil.isEmpty(value) && values.length < 2) {
                e.getSections().remove(type);
            } else {
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        e.setSection(type, new TextSection(value));
                        break;
                    case QUALIFICATIONS:
                        e.setSection(type, new ListSection(value.split("\\n")));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> organizations = new ArrayList<>();
                        String[] urls = req.getParameterValues(type.name() + "url");
                        for (int i = 0; i < values.length; i++) {
                            String name = values[i];
                            if (!HtmlUtil.isEmpty(name)) {
                                List<Organization.Position> positions = new ArrayList<>();
                                String pfx = type.name() + i;
                                String[] startDates = req.getParameterValues(pfx + "startDate");
                                String[] endDates = req.getParameterValues(pfx + "endDate");
                                String[] titles = req.getParameterValues(pfx + "title");
                                String[] descriptions = req.getParameterValues(pfx + "description");
                                for (int j = 0; j < titles.length; j++) {
                                    if (!HtmlUtil.isEmpty(titles[j])) {
                                        positions.add(new Organization.Position(DateUtil.parse(startDates[j]), DateUtil.parse(endDates[j]), titles[j], descriptions[j]));
                                    }
                                }
                                organizations.add(new Organization(new Link(name, urls[i]), positions));
                            }
                        }
                        e.setSection(type, new OrganizationSection(organizations));
                        break;
                    default:
                        break;
                }
            }
        }
        if (isCreate) {
            storage.save(e);
        } else {
            storage.update(e);
        }
        resp.sendRedirect("employees");
    }
}
