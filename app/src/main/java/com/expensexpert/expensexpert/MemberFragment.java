package com.expensexpert.expensexpert;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MemberFragment extends Fragment {

    private List<Member> memberlist;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    int GroupId;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_members, container, false);


        recyclerView = root.findViewById(R.id.member_list);
        memberlist = new ArrayList<Member>();
        fab = root.findViewById(R.id.add_member);

        Bundle bundle = this.getArguments();
        GroupId = bundle.getInt("GroupId");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddMember.class);
                intent.putExtra("GroupId", GroupId);
                startActivity(intent);
            }
        });

        setMemberInfo();
        setAdapter();
        return root;
    }

    private void setAdapter() {
        MemberAdapter adapter = new MemberAdapter(memberlist);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMemberInfo() {
        DatabaseHelper db = new DatabaseHelper(getContext());
        List<Contributors> everyone = db.get_Contributors(GroupId);

        for(int i=0; i<everyone.size(); i++){
            double gave = db.get_Expense_Amount(db.get_Contributor_Expense_deactive(GroupId, everyone.get(i).getId()));
            double spent = db.get_Expense_Amount_div(db.get_Contributor_Expense_active(GroupId, everyone.get(i).getId()));
            Member member = new Member(everyone.get(i).getId(), everyone.get(i).getName(), gave, spent);
            memberlist.add(member);
        }
    }
}
