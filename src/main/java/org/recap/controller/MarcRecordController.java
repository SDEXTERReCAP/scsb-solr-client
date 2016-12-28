package org.recap.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.recap.model.search.BibliographicMarcForm;
import org.recap.model.userManagement.UserDetailsForm;
import org.recap.security.UserManagement;
import org.recap.util.MarcRecordViewUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * Created by rajeshbabuk on 22/7/16.
 */
@Controller
public class MarcRecordController {

    Logger logger = LoggerFactory.getLogger(MarcRecordController.class);

    @Autowired
    MarcRecordViewUtil marcRecordViewUtil;

    @RequestMapping("/openMarcRecord")
    public String openMarcRecord(@Valid @ModelAttribute("bibId") Integer bibId, Model model) {
        Subject subject= SecurityUtils.getSubject();
        UserDetailsForm userDetailsForm=UserManagement.getRequestAccess(subject);
        BibliographicMarcForm bibliographicMarcForm = marcRecordViewUtil.buildBibliographicMarcForm(bibId, null,userDetailsForm);
        model.addAttribute("bibliographicMarcForm", bibliographicMarcForm);
        if (null != bibliographicMarcForm && StringUtils.isNotBlank(bibliographicMarcForm.getErrorMessage())) {
            return "marcRecordErrorMessage";
        } else {
            return "marcRecordView";
        }
    }

}
