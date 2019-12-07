package com.d20charactersheet.adventurebookresolver.nativeapp.gui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.d20charactersheet.adventurebookresolver.nativeapp.Logger

abstract class LogFragment : Fragment() {

    override fun onAttach(context: Context) {
        Logger.info(javaClass.simpleName + ".onAttach")
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.info(javaClass.simpleName + ".onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Logger.info(javaClass.simpleName + ".onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Logger.info(javaClass.simpleName + ".onActivityCreated")
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        Logger.info(javaClass.simpleName + ".onStart")
        super.onStart()
    }

    override fun onResume() {
        Logger.info(javaClass.simpleName + ".onResume")
        super.onResume()
    }

    override fun onPause() {
        Logger.info(javaClass.simpleName + ".onPause")
        super.onPause()
    }

    override fun onStop() {
        Logger.info(javaClass.simpleName + ".onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        Logger.info(javaClass.simpleName + ".onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Logger.info(javaClass.simpleName + ".onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Logger.info(javaClass.simpleName + ".onDetach")
        super.onDetach()
    }
}
