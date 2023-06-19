package com.easyfitness;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeRequestInitializer;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FitnessVideosFragment extends Fragment implements VideoAdapter.OnClickListener{
    private int id;
    private MainActivity mActivity = null;

    private int getIdFromArguments() {
        Bundle args = getArguments();
        if (args != null) {
            return args.getInt("id");
        }
        return -1; // Default value if the argument is not set
    }

    public MainActivity getMainActivity() {
        return this.mActivity;
    }

    public static FitnessVideosFragment newInstance(String name, int id) {
        FitnessVideosFragment f = new FitnessVideosFragment();

        // Supply name and id as arguments.
        Bundle args = new Bundle();
        args.putString("name", name);
        args.putInt("id", id);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mActivity = (MainActivity) context;
    }

    private List<VideoItem> videoList;
    private RecyclerView videosRecyclerView;
    private VideoAdapter videoAdapter;

    @Override
    public void onItemClick(VideoItem videoItem) {
        // Handle the click event for the video item here
        // Open a new fragment or perform any desired action
        // You can use the videoItem object to access the clicked video's details
    }

    public FitnessVideosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoList = new ArrayList<>();
        videoAdapter = new VideoAdapter(videoList, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fitness_videos, container, false);

        // Initialize and set up the RecyclerView and its adapter
        videosRecyclerView = view.findViewById(R.id.videosRecyclerView);
        videosRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        videosRecyclerView.setAdapter(videoAdapter);

        // Perform the YouTube search and populate the video list
        new YouTubeSearchTask().execute();

        return view;
    }

    private class YouTubeSearchTask extends AsyncTask<Void, Void, List<VideoItem>> {

        @Override
        protected List<VideoItem> doInBackground(Void... params) {
            // Define the search query and parameters
            String query = "fitness workout";
            long maxResults = 10;

            // Initialize the YouTube API client
            YouTube youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), null)
                    .setYouTubeRequestInitializer(new YouTubeRequestInitializer("AIzaSyDzzdLOWBcI42JRdzAm5YRcwqtFtnUeo_E"))
                    .setApplicationName("PersonalFit-Tracker")
                    .build();

            // Execute the search request
            try {
                YouTube.Search.List searchList = youtube.search().list("snippet");
                searchList.setQ(query);
                searchList.setMaxResults(maxResults);
                SearchListResponse response = searchList.execute();

                // Process the search results
                List<SearchResult> searchResults = response.getItems();
                List<VideoItem> videoItems = new ArrayList<>();
                for (SearchResult result : searchResults) {
                    // Extract information from each search result (e.g., video ID, title, thumbnail)
                    String videoId = result.getId().getVideoId();
                    String title = result.getSnippet().getTitle();
                    String thumbnailUrl = result.getSnippet().getThumbnails().getDefault().getUrl();
                    String details = result.getSnippet().getDescription(); // Extract the video details

                    // Create a new VideoItem object with the extracted information
                    VideoItem videoItem = new VideoItem(videoId, title, thumbnailUrl, details);

                    // Add the VideoItem to the videoItems list
                    videoItems.add(videoItem);
                }
                return videoItems;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<VideoItem> videoItems) {
            if (videoItems != null) {
                // Clear the existing video list and add the new video items
                videoList.clear();
                videoList.addAll(videoItems);
                // Notify the adapter of the data changes
                videoAdapter.notifyDataSetChanged();
            }
        }
    }
}

//import android.content.Context;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentTransaction;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.services.youtube.YouTube;
//import com.google.api.services.youtube.YouTubeRequestInitializer;
//import com.google.api.services.youtube.model.SearchListResponse;
//import com.google.api.services.youtube.model.SearchResult;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import de.psdev.licensesdialog.LicensesDialog;
//import de.psdev.licensesdialog.licenses.License;
//import de.psdev.licensesdialog.model.Notice;
//
//public class FitnessVideosFragment extends Fragment implements OnClickListener  {
//    private String name;
//    private int id;
//    private MainActivity mActivity = null;
//
//    private int getIdFromArguments() {
//            Bundle args = getArguments();
//            if (args != null) {
//                return args.getInt("id");
//            }
//            return -1; // Default value if the argument is not set
//        }
//
//
//
////    private final View.OnClickListener clickLicense = v -> {
//
////        String name = null;
////        String url = null;
////        String copyright = null;
////        License license = null;
////
////        switch (v.getId()) {
////
////
////
////
////
////
////
////
////        }
////
////        final Notice notice = new Notice(name, url, copyright, license);
////        new LicensesDialog.Builder(getMainActivity())
////                .setNotices(notice)
////                .build()
////                .show();
////    };
//
//    public MainActivity getMainActivity() {
//        return this.mActivity;
//    }
//    public static FitnessVideosFragment newInstance(String name, int id) {
//        FitnessVideosFragment f = new FitnessVideosFragment();
//
//        // Supply name and id as arguments.
//        Bundle args = new Bundle();
//        args.putString("name", name);
//        args.putInt("id", id);
//        f.setArguments(args);
//
//        return f;
//    }
//
//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        this.mActivity = (MainActivity) context;
//    }
//
//    private List<VideoItem> videoList;
//    private RecyclerView videosRecyclerView;
//    private VideoAdapter videoAdapter;
//    @Override
//    public void onItemClick(VideoItem videoItem) {
//        // Handle the click event for the video item here
//        // Open a new fragment or perform any desired action
//        // You can use the videoItem object to access the clicked video's details
//    }
//
//    public interface OnClickListener {
//        void onItemClick(VideoItem videoItem);
//    }
//
//
//    public FitnessVideosFragment() {
//        // Required empty public constructor
//    }
////    private void replaceFragment(FitnessVideosFragment fragment) {
////        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
////        transaction.replace(R.id.container, fragment);
////        transaction.addToBackStack(null);
////        transaction.commit();
////    }
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        videoList = new ArrayList<>();
//        videoAdapter = new VideoAdapter(videoList, this);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_fitness_videos, container, false);
//
//        // Initialize and set up the RecyclerView and its adapter
//        videosRecyclerView = view.findViewById(R.id.videosRecyclerView);
//        videosRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        videosRecyclerView.setAdapter(videoAdapter);
//
//        // Perform the YouTube search and populate the video list
//        new YouTubeSearchTask().execute();
//
//        return view;
//    }
//
//    private class YouTubeSearchTask extends AsyncTask<Void, Void, List<VideoItem>> {
//
//        @Override
//        protected List<VideoItem> doInBackground(Void... params) {
//            // Define the search query and parameters
//            String query = "fitness workout";
//            long maxResults = 10;
//
//            // Initialize the YouTube API client
//            YouTube youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), null)
//                    .setYouTubeRequestInitializer(new YouTubeRequestInitializer("YOUR_API_KEY"))
//                    .setApplicationName("PersonalFit-Tracker")
//                    .build();
//
//            // Execute the search request
//            try {
//                YouTube.Search.List searchList = youtube.search().list("snippet");
//                searchList.setQ(query);
//                searchList.setMaxResults(maxResults);
//                SearchListResponse response = searchList.execute();
//
//                // Process the search results
//                List<SearchResult> searchResults = response.getItems();
//                List<VideoItem> videoItems = new ArrayList<>();
//                for (SearchResult result : searchResults) {
//                    // Extract information from each search result (e.g., video ID, title, thumbnail)
//                    String videoId = result.getId().getVideoId();
//                    String title = result.getSnippet().getTitle();
//                    String thumbnailUrl = result.getSnippet().getThumbnails().getDefault().getUrl();
//                    String details = result.getSnippet().getDescription(); // Extract the video details
//
//                    // Create a new VideoItem object with the extracted information
//                    VideoItem videoItem = new VideoItem(videoId, title, thumbnailUrl, details);
//
//                    // Add the VideoItem to the videoItems list
//                    videoItems.add(videoItem);
//                }
//                return videoItems;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(List<VideoItem> videoItems) {
//            if (videoItems != null) {
//                // Clear the existing video list and add the new video items
//                videoList.clear();
//                videoList.addAll(videoItems);
//                // Notify the adapter of the data changes
//                videoAdapter.notifyDataSetChanged();
//            }
//        }
//    }
//}
//
//







//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.services.youtube.YouTube;
//import com.google.api.services.youtube.YouTubeRequestInitializer;
//import com.google.api.services.youtube.model.SearchListResponse;
//import com.google.api.services.youtube.model.SearchResult;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class FitnessVideosFragment extends Fragment {
//
//    private List<VideoItem> videoList;
//    private RecyclerView videosRecyclerView;
//    private VideoAdapter videoAdapter;
//
//
//    public FitnessVideosFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        videoList = new ArrayList<>();
//        videoAdapter = new VideoAdapter(videoList);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_fitness_videos, container, false);
//
//        // Perform the YouTube search and populate the video list
//        performYouTubeSearch();
//
//        // Initialize and set up the RecyclerView and its adapter
//        RecyclerView recyclerView = view.findViewById(R.id.videosRecyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        //VideoAdapter videoAdapter = new VideoAdapter(videoList);
//        recyclerView.setAdapter(videoAdapter);
//        return view;
//    }
//
//    private void performYouTubeSearch() {
//        // Define the search query and parameters
//        String query = "fitness workout";
//        long maxResults = 10;
//
//        // Initialize the YouTube API client
//        YouTube youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), null)
//                .setYouTubeRequestInitializer(new YouTubeRequestInitializer("YOUR_API_KEY"))
//                .setApplicationName("PersonalFit-Tracker")
//                .build();
//
//        // Execute the search request
//        try {
//            YouTube.Search.List searchList = youtube.search().list("snippet");
//            searchList.setQ(query);
//            searchList.setMaxResults(maxResults);
//            SearchListResponse response = searchList.execute();
//
//            // Process the search results
//            List<SearchResult> searchResults = response.getItems();
//            for (SearchResult result : searchResults) {
//                // Extract information from each search result (e.g., video ID, title, thumbnail)
//                String videoId = result.getId().getVideoId();
//                String title = result.getSnippet().getTitle();
//                String thumbnailUrl = result.getSnippet().getThumbnails().getDefault().getUrl();
//                String details = result.getSnippet().getDescription(); // Extract the video details
//
//                // Create a new VideoItem object with the extracted information
//                VideoItem videoItem = new VideoItem(videoId, title, thumbnailUrl,details);
//
//                // Add the VideoItem to the videoList
//                videoList.add(videoItem);
//                // Notify the adapter of the data changes
//                videoAdapter.notifyDataSetChanged();
//
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}

//    public FitnessVideosFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        videoList = new ArrayList<>();
//    }
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_fitness_videos, container, false);
//
//        // Perform the YouTube search and populate the video list
//        performYouTubeSearch();
//
//        // Initialize and set up the RecyclerView and its adapter
//        RecyclerView recyclerView = view.findViewById(R.id.videosRecyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        VideoAdapter videoAdapter = new VideoAdapter(videoList);
//        recyclerView.setAdapter(videoAdapter);
//
//        return view;
//    }
//
//    private void performYouTubeSearch() {
//        // Define the search query and parameters
//        String query = "fitness workout";
//        long maxResults = 10;
//
//        // Initialize the YouTube API client
//        YouTube youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), null)
//                .setYouTubeRequestInitializer(new YouTubeRequestInitializer("AIzaSyDzzdLOWBcI42JRdzAm5YRcwqtFtnUeo_E"))
//                .setApplicationName("PersonalFit-Tracker")
//                .build();
//
//        // Execute the search request
//        try {
//            YouTube.Search.List searchList = youtube.search().list("snippet");
//            searchList.setQ(query);
//            searchList.setMaxResults(maxResults);
//            SearchListResponse response = searchList.execute();
//
//            // Process the search results
//            List<SearchResult> searchResults = response.getItems();
//            for (SearchResult result : searchResults) {
//                // Extract information from each search result (e.g., video ID, title, thumbnail)
//                String videoId = result.getId().getVideoId();
//                String title = result.getSnippet().getTitle();
//                String thumbnailUrl = result.getSnippet().getThumbnails().getDefault().getUrl();
//
//                // Create a new VideoItem object with the extracted information
//                VideoItem videoItem = new VideoItem(videoId, title, thumbnailUrl);
//
//                // Add the VideoItem to the videoList
//                videoList.add(videoItem);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
