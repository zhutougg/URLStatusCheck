package com.zhutougg.URLCheck;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class MainUI {

	private JFrame frmUrl;
	private JTextField uriText;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainUI window = new MainUI();
					window.frmUrl.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmUrl = new JFrame();
		frmUrl.setTitle("URL状态码检测工具");
		frmUrl.setBounds(100, 100, 800, 550);
		frmUrl.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmUrl.setResizable(false);

		
		JLabel lblNewLabel = new JLabel("待检测URL:");

		JScrollPane scrollPane = new JScrollPane();
		JTextArea sourceURL = new JTextArea();
		scrollPane.setViewportView(sourceURL);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		
		JLabel lblNewLabel_2 = new JLabel("检测结果");
		
		JScrollPane resultScrollPane = new JScrollPane();
		JTextArea resultURL = new JTextArea();
		resultScrollPane.setViewportView(resultURL);
		resultScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		resultScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		uriText = new JTextField();
		uriText.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("需要检测的路径：");

		JButton btnNewButton = new JButton("检测");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String URI = uriText.getText();
				String[] source = sourceURL.getText().split("\n");
				List<String> list = Arrays.asList(source);
				list.parallelStream().forEach(a -> {
					String line = httpGet(a+URI);
					resultURL.append(line+"\n");
				});
			}
		});
		

		
		
		
		GroupLayout groupLayout = new GroupLayout(frmUrl.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(37)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(40)
									.addComponent(lblNewLabel_1))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(18)
									.addComponent(uriText, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(46)
									.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
							.addComponent(resultScrollPane, GroupLayout.PREFERRED_SIZE, 304, GroupLayout.PREFERRED_SIZE)
							.addGap(30))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.RELATED, 419, Short.MAX_VALUE)
							.addComponent(lblNewLabel_2)
							.addGap(234))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addGap(76)
					.addComponent(lblNewLabel_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(uriText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(95)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(253, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(46, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(lblNewLabel_2))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 444, GroupLayout.PREFERRED_SIZE)
						.addComponent(resultScrollPane, GroupLayout.PREFERRED_SIZE, 442, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		
		
		
		
		frmUrl.getContentPane().setLayout(groupLayout);
	}

	public static String httpGet(String url) {
		try {
		HttpGet httpGet = new HttpGet(url);
		RequestConfig config = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();
		CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(config).build();
		CloseableHttpResponse response = httpclient.execute(httpGet);
		StatusLine line = response.getStatusLine();
		return url+"\t"+line;
		} catch (IOException e) {
			return url+"    出现异常";
		}
	}
}
