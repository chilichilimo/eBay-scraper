package comp3013.group3.ebayscraper.mailer;

import org.immutables.value.Value;

@Value.Style(stagedBuilder = true)
@Value.Immutable
public interface ItemInfo {
	String name();
	String url();
	double price();
}
