package org.redmine.netbeansRedmineIntegration;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.NbPreferences;
import org.redmine.ta.RedmineManager;
import org.redmine.ta.beans.Issue;

public final class netbeansRedmineIntegrationAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        String redmineHost = NbPreferences.forModule(RedmineIntegrationPanel.class).get("redmineURL", "");
        String apiAccessKey = NbPreferences.forModule(RedmineIntegrationPanel.class).get("redmineAPI", "");
        RedmineManager mgr = new RedmineManager(redmineHost, apiAccessKey);

        NotifyDescriptor.InputLine question;
        question = new NotifyDescriptor.InputLine("Project Code:",
                "What project code are you working on.",
                NotifyDescriptor.OK_CANCEL_OPTION,
                NotifyDescriptor.QUESTION_MESSAGE);

        if (DialogDisplayer.getDefault().notify(question) == NotifyDescriptor.OK_OPTION) {
            String projectKey = question.getInputText();

            NotifyDescriptor.InputLine question2;
            question2 = new NotifyDescriptor.InputLine("Issue Title:",
                    "What is your problem?",
                    NotifyDescriptor.OK_CANCEL_OPTION,
                    NotifyDescriptor.QUESTION_MESSAGE);

            if (DialogDisplayer.getDefault().notify(question2) == NotifyDescriptor.OK_OPTION) {
                String issueTitle = question2.getInputText();

                Issue issueToCreate = new Issue();
                issueToCreate.setSubject(issueTitle);

                try {
                Issue newIssue = mgr.createIssue(projectKey, issueToCreate);
                } catch (Throwable tr) {

                }

                String msg = "Issue Created";
                int msgType = NotifyDescriptor.INFORMATION_MESSAGE;
                NotifyDescriptor d = new NotifyDescriptor.Message(msg, msgType);
                DialogDisplayer.getDefault().notify(d);
            }
        }
    }
}
