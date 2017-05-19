/*
 * A simple libtorrent client for android using JNI
 */
#include <string>
#include <jni.h>
#include <libtorrent/torrent_handle.hpp>
#include <libtorrent/session.hpp>
#include <libtorrent/add_torrent_params.hpp>
#include <libtorrent/torrent_info.hpp>

// Expose our JNI functions
extern "C" {
	jstring Java_com_example_hellolibtorrent_HelloLibtorrent_startTorrent( JNIEnv* env, jobject thiz, jstring torHash, jstring sdcardPathA );
	jstring Java_com_example_hellolibtorrent_HelloLibtorrent_getStatusA( JNIEnv* env, jobject thiz, jstring sdcardPathA );
	jstring Java_com_example_hellolibtorrent_HelloLibtorrent_pauseTorrents( JNIEnv* env, jobject thiz);
	jstring Java_com_example_hellolibtorrent_HelloLibtorrent_resumeTorrents( JNIEnv* env, jobject thiz);
}

// Set variables
//char* torrent_file = "/storage/sdcard1/Torrents/test.torrent";
//std::string torrent_magnet = "magnet:?xt=urn:btih:20ae548a6e159f9473dff61cacd7fd9644b4d626&dn=Family+Guy+S13E01+HDTV+x264-2HD+%5Beztv%5D&xl=274235363&dl=274235363&tr=udp://open.demonii.com:1337/announce&tr=udp://tracker.publicbt.com:80/announce&tr=udp://tracker.openbittorrent.com:80/announce&tr=udp://tracker.istole.it:80/announce";
//char* save_location = "/storage/sdcard1/Torrents/";

libtorrent::session s;
libtorrent::add_torrent_params p;
libtorrent::torrent_handle h;

//std::string f_at = h.get_torrent_info().file_at(0).path.str(); // torrent file location ***IMPORTANT***

//std::string str(h.get_torrent_info().file_at(0).path.str());
//boost::filesystem::path f_at = h.get_torrent_info().file_at(0).path.str();


/* 
 * Starts downloading the torrent and returns the path of the torrent file we are downloading
 */
jstring Java_com_example_hellolibtorrent_HelloLibtorrent_startTorrent( JNIEnv* env, jobject thiz, jstring torHash, jstring sdcardPathA ) {
	std::string result = "";

	try {
		h.set_sequential_download(true);
		s.listen_on(std::make_pair(6881, 6889));

		const char *nativeStringB = env->GetStringUTFChars(sdcardPathA, JNI_FALSE);
		p.save_path = nativeStringB;
		env->ReleaseStringUTFChars(sdcardPathA, nativeStringB);

    	//p.save_path = save_location;

    	//p.ti = new libtorrent::torrent_info(torrent_file);
    	//p.url = new libtorrent::torrent_info(parseMag);

    	const char *nativeString = env->GetStringUTFChars(torHash, JNI_FALSE);
    	p.url = nativeString;
    	env->ReleaseStringUTFChars(torHash, nativeString);

    	//p.url = torrent_magnet;


    	h = s.add_torrent(p);

    	result = "Started torrent";
    } catch(std::exception& e) {
    	result = e.what();
    }

	return env->NewStringUTF(result.c_str());
}

/*
 * Returns the current status of the torrent in a single string
 */
int counter = 0;
jstring Java_com_example_hellolibtorrent_HelloLibtorrent_getStatusA( JNIEnv* env, jobject thiz, jstring sdcardPathA ) {
	std::stringstream result;

	const char *nativeStringC = env->GetStringUTFChars(sdcardPathA, JNI_FALSE);
		std::string sdLoc =  nativeStringC;
	env->ReleaseStringUTFChars(sdcardPathA, nativeStringC);

	if (h.is_valid()) {
		libtorrent::torrent_status status = h.status();
		counter++;
		result
			<< "  Status update " << counter << std::endl
			//<< "  File " << f_at << std::endl
			<< "  Location: " << sdLoc << std::endl
			<< "  Name: " << h.name() << std::endl
		    << "  Progress: " << (status.progress * 100.0) << "%" << std::endl
		    << "  Download rate: " << (status.download_rate /1000.0) << " KBps" << std::endl
		    << "  Upload rate: " << (status.upload_rate /1000.0) << " KBps" << std::endl
		    << "  Seeds: " << status.num_seeds << std::endl
		    << "  Peers: " << status.num_peers;
	} else {
		result << "Handle is not valid";
	}
	
	return env->NewStringUTF(result.str().c_str());
}

jstring Java_com_example_hellolibtorrent_HelloLibtorrent_pauseTorrents(JNIEnv* env, jobject thiz) {
	std::stringstream result;

	result << "pause trigger return";

	s.pause();
	return env->NewStringUTF(result.str().c_str());
}

jstring Java_com_example_hellolibtorrent_HelloLibtorrent_resumeTorrents(JNIEnv* env, jobject thiz) {
	std::stringstream result;

		result << "resume trigger return";

		s.resume();
		return env->NewStringUTF(result.str().c_str());
	s.resume();
}


