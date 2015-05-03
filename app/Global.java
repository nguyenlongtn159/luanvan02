import java.util.List;
import java.util.Map;

import models.Detai;
import models.Tag;
import models.UserAccount;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.api.mvc.EssentialFilter;
import play.libs.Yaml;
import play.filters.csrf.CSRFFilter;
import com.avaje.ebean.Ebean;

public class Global extends GlobalSettings {

	public void onStart(Application app) {
		// TODO Auto-generated method stub
		Logger.info("Appilication start...");
		InitatialData.insert(app);

	}

	static class InitatialData {
		public static void insert(Application app) {
			Map<String, List<Object>> all = (Map<String, List<Object>>) Yaml
					.load("initial-data.yml");
			if (Ebean.find(Tag.class).findRowCount() == 0) {
				Ebean.save(all.get("tags"));
			}
			if (Ebean.find(UserAccount.class).findRowCount() == 0) {
				Ebean.save(all.get("user_accounts"));
				List<UserAccount> list=UserAccount.findAll();
				for(UserAccount user: list){
					user.tag=Tag.findById(user.chucvu);
					user.update();
				}
				
			}
			if(Ebean.find(Detai.class).findRowCount()==0){
				Ebean.save(all.get("detai"));
				List<UserAccount> list=UserAccount.findAll();
				for(UserAccount user: list){
					user.chuyenmon=Detai.findById(user.cmon);
					user.update();
				}
			}
		}
	}

	public void onStop(Application app) {
		// TODO Auto-generated method stub
		Logger.info("Appilication shutdown...");
	}

	@Override
    public <T extends EssentialFilter> Class<T>[] filters() {
        return new Class[]{CSRFFilter.class};
    }
}