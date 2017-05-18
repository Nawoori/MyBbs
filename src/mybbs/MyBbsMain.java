package mybbs;

import com.nawoori.mybbs.presenter.BbsPresenter;

public class MybbsMain {
	public static void main(String[] args) {
		BbsPresenter presenter = new BbsPresenter();
		presenter.start();
	}
}
